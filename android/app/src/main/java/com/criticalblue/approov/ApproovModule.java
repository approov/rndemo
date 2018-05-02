package com.criticalblue.approov;

import android.content.Context;
import android.util.Base64;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.lang.IllegalArgumentException;
import java.net.MalformedURLException;

import com.criticalblue.attestationlibrary.ApproovAttestation;
import com.criticalblue.attestationlibrary.ApproovConfig;
import com.criticalblue.attestationlibrary.TokenInterface;

class ApproovModule extends ReactContextBaseJavaModule {

    private static final String E_APPROOV_ERROR = "E_APPROOV_ERROR";

    private Context context;

    public ApproovModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;

        try {
            ApproovConfig config = ApproovConfig.getDefaultConfig(reactContext);
            ApproovAttestation.initialize(config);
        } catch (IllegalArgumentException ex) {
        } catch (MalformedURLException ex) {
        }
    }

    @Override
    public String getName() {
        return "Approov";
    }

    @ReactMethod
    public void fetchApproovTokenCB(final String url, final Callback onSuccess, final Callback onFailure) {

        ApproovAttestation.shared().fetchApproovToken(new TokenInterface() {
            @Override
            public void approovTokenFetchResult(ApproovResults results) {
                if (results.getResult() == ApproovAttestation.AttestationResult.SUCCESS)
                    onSuccess.invoke(results.getToken());
                else
                    onFailure.invoke("Failed to fetch Approov Token");
            }
        }, url);
    }

    @ReactMethod
    public void fetchApproovToken(final String url, final Promise promise) {

        ApproovAttestation.shared().fetchApproovToken(new TokenInterface() {
            @Override
            public void approovTokenFetchResult(ApproovResults results) {
                if (results.getResult() == ApproovAttestation.AttestationResult.SUCCESS)
                    promise.resolve(results.getToken());
                else
                    promise.reject(E_APPROOV_ERROR, "Failed to fetch Approov Token");
            }
        }, url);
    }

    @ReactMethod
    public void getCert(final String url, final Callback onSuccess, final Callback onFailure) {

        byte[] certBytes = ApproovAttestation.shared().getCert(url);
        if (certBytes == null)
            onFailure.invoke("Failed to get certificate data");
        else
            onSuccess.invoke(new String(Base64.encode(certBytes, Base64.NO_WRAP)));
    }
}

// end of file
