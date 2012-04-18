//
//  ViewController.h
//  RestKitSample
//
//  Created by exo on 2/22/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <RestKit/RestKit.h>

@interface ViewController : UIViewController <UITextFieldDelegate, RKRequestDelegate> {
    NSMutableDictionary *userAndId;
}
@property (retain, nonatomic) IBOutlet UITextField *username;
@property (retain, nonatomic) IBOutlet UITextField *password;
@property (retain, nonatomic) IBOutlet UITextView *textView;

- (IBAction)getNewActivities:(id)sender;
@end
