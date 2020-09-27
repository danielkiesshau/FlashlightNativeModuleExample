//
//  Torch.swift
//  swiftNativeModule
//
//  Created by Daniel Kiesshau on 26/09/20.
//

import Foundation
import AVFoundation

@objc(Torch)
class Torch: RCTEventEmitter {
  // pass uderline to be able to pass parameter in rn
  @objc
  func toggleTorch(_ on: Bool) -> Void {
      print("Torch STARTED")
      sendEvent(withName: "OnChange", body: on)
      guard
          let device = AVCaptureDevice.default(for: AVMediaType.video),
          device.hasTorch
      else { return }

      do {
          try device.lockForConfiguration()
          device.torchMode = on ? .on : .off
          device.unlockForConfiguration()
      } catch {
          print("Torch could not be used")
      }
  }
  
  override func supportedEvents() -> [String]! {
    return ["OnChange"]
  }
  
}
