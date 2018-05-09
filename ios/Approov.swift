//  rndemo Approov.swift

import Foundation
import Approov

@objc(Approov)
class Approov: NSObject {
  
  @objc func fetchApproovToken(_ url: String,
                        resolver resolve: @escaping RCTPromiseResolveBlock,
                        rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
    let attestee = ApproovAttestee.shared()
    attestee?.fetchApproovToken({ (tokenFetchData: ApproovTokenFetchData) in
      switch tokenFetchData.result {
      case .successful:
        resolve(tokenFetchData.approovToken)
      case .failed:
        resolve("NO_TOKEN")
      }
    }, url)
  }
}

// end of file
