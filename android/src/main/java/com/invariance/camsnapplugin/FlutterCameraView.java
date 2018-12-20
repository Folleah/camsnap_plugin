package com.invariance.camsnapplugin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.View;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import static io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class FlutterCameraView implements PlatformView, MethodChannel.MethodCallHandler
{
    private final CameraPreview camPreview;
    private final Camera camera;
    private final MethodChannel methodChannel;

    FlutterCameraView(Context context, BinaryMessenger messenger, int id) {
        if (!checkCameraHardware(context)) {
            Log.d("camera_preview","Camera not found.");
        }
        camera = getCameraInstance();

        camPreview = new CameraPreview(context, camera);
        methodChannel = new MethodChannel(messenger, "com.invariance.camsnapplugin/cameraview_" + id);
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public View getView() {
        return camPreview;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "captureImage":
                captureImage(methodCall, result);
                break;
            default:
                result.notImplemented();
        }
    }

    private void captureImage(MethodCall methodCall, Result result) {
        String location = (String) methodCall.arguments;
        // save image
        //textView.setText(location);
        result.success(null);
    }

    @Override
    public void dispose() {}

    /** Check if this device has a camera */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.e("camera", e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }
}
