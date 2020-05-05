package dpa_me.com.dpa_pubproc.Units;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static dpa_me.com.dpa_pubproc.Units.PubProc.ApplicationID;

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
            app_json.put("app_id", ApplicationID);
        }catch (Exception ignored){}

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();

        final RetroInterface retroInterface = new Retrofit.Builder().baseUrl("http://volcan.ir").
                addConverterFactory(ScalarsConverterFactory.create()).
                client(client).
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
