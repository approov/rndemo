//  rndemo Approov.m

#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(Approov, NSObject)

RCT_EXTERN_METHOD(fetchApproovToken:(NSString *)url
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )

@end

// end of file
