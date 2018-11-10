package dpa_me.com.dpa_pubproc.Units;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FindServer {

    public FindServer(){
        super();
    }

    public interface IOpration{
        void onSuccess();
        void onFailed();
        void onMaintenanceBreak();
    }

    private IOpration onOpration = null;

    public FindServer setOnDone(IOpration onOpration){
        this.onOpration = onOpration;
        return this;
    }

    public void setServerName(){
        final JSONObject app_json = new JSONObject();
        try {
            app_json.put("app_id", "0c63f2aba12d4756a23f47b0161d3594");
        }catch (Exception ignored){}

        final RetroInterface retroInterface = new Retrofit.Builder().baseUrl("http://restook.ir").
                addConverterFactory(ScalarsConverterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build().create(RetroInterface.class);

        retroInterface.getAddress_1(app_json.toString()).enqueue(new Callback<AppAddressModel>() {
            @Override
            public void onResponse(Call<AppAddressModel> call, Response<AppAddressModel> response) {
                if (response.body().maintenance_break == 1 && onOpration != null)
                    onOpration.onMaintenanceBreak();
                else {
                    PubProc.HttpRootAddress = response.body().address_1;
                    if (onOpration != null)
                        onOpration.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<AppAddressModel> call, Throwable t) {
                retroInterface.getAddress_2(app_json.toString()).enqueue(new Callback<AppAddressModel>() {
                    @Override
                    public void onResponse(Call<AppAddressModel> call, Response<AppAddressModel> response) {
                        if (response.body().maintenance_break == 1 && onOpration != null)
                            onOpration.onMaintenanceBreak();
                        else {
                            PubProc.HttpRootAddress = response.body().address_1;
                            if (onOpration != null)
                                onOpration.onSuccess();
                        }
                    }

                    @Override
                    public void onFailure(Call<AppAddressModel> call, Throwable t) {
                        retroInterface.getAddress_3(app_json.toString()).enqueue(new Callback<AppAddressModel>() {
                            @Override
                            public void onResponse(Call<AppAddressModel> call, Response<AppAddressModel> response) {
                                if (response.body().maintenance_break == 1 && onOpration != null)
                                    onOpration.onMaintenanceBreak();
                                else {
                                    PubProc.HttpRootAddress = response.body().address_1;
                                    if (onOpration != null)
                                        onOpration.onSuccess();
                                }
                            }

                            @Override
                            public void onFailure(Call<AppAddressModel> call, Throwable t) {
                                if (onOpration != null)
                                    onOpration.onFailed();
                            }
                        });
                    }
                });
            }
        });
    }
}
