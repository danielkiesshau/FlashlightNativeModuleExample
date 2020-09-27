//
//  Torch.m
//  swiftNativeModule
//
//  Created by Daniel Kiesshau on 27/09/20.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>


@interface RCT_EXTERN_MODULE(Torch, RCTEventEmitter)
RCT_EXTERN_METHOD(toggleTorch: (BOOL)On)
@end
