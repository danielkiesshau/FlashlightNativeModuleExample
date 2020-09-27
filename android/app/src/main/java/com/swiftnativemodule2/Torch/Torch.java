package com.swiftnativemodule2.Torch;


import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Torch extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private CameraManager mCameraManager;
    private String mCameraId;

    Torch(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "Torch";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void toggleTorch(Boolean on) {
        Boolean torchAvailable = reactContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (torchAvailable) {

            mCameraManager = (CameraManager) getReactApplicationContext().getSystemService(ReactContext.CAMERA_SERVICE);
            try {
                mCameraId = mCameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            WritableMap params = Arguments.createMap();
            params.putBoolean("OnChange", on);
            switchFlashLight(on);

            sendEvent(reactContext, "OnChange", on);
            Log.i("TORCH", "Disponível");
        } else {
            Log.i("TORCH", "Não disponível");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable Boolean bool) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, bool);
    }
}
