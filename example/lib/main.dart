import 'package:flutter/material.dart';
import 'package:camsnap_plugin/camera_view.dart';

void main() => runApp(MaterialApp(home: CameraViewExample()));

class CameraViewExample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    double screenHeight = MediaQuery.of(context).size.height;
    double screenWidth = MediaQuery.of(context).size.width;

    return Scaffold(
        //appBar: AppBar(title: const Text('Flutter CameraView example')),
        body: Column(children: [
          Center(
              child: Container(
                  width: screenWidth,
                  height: screenHeight,
                  child: CameraView(
                    onCameraViewCreated: _onCameraViewCreated,
                  ))),
        ]));
  }

  void _onCameraViewCreated(CameraViewController controller) {
    controller.captureImage('test');
  }
}