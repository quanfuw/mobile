//
//  ViewController.h
//  PromoTest
//
//  Created by exo on 2/22/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ASIHTTPRequest.h"
#import "ASIFormDataRequest.h"
#import "SBJson.h"

@interface ViewController : UIViewController <UITextFieldDelegate, ASIHTTPRequestDelegate>
@property (retain, nonatomic) IBOutlet UITextField *textField;
@property (retain, nonatomic) IBOutlet UITextView *textView;

@end
