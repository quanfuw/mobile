//
//  TimerViewController.h
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TimerViewController : UIViewController
@property (retain, nonatomic) IBOutlet UIDatePicker *myPicker;
@property (retain, nonatomic) IBOutlet UILabel *label;
@property (retain, nonatomic) IBOutlet UIButton *startButton;
- (IBAction)echoTime:(id)sender;
- (void)echoIt:(NSTimer*)timer;
@end
