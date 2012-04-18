//
//  ViewController.h
//  MonkeyPinch
//
//  Created by exo on 2/29/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import "TickleGestureRecognizer.h"

@interface ViewController : UIViewController <UIGestureRecognizerDelegate>

@property (strong) AVAudioPlayer *avPlayer;
@property (strong) AVAudioPlayer *hehePlayer;

- (void)handlePan:(UIPanGestureRecognizer*)recognizer;
- (void)handleTickle:(TickleGestureRecognizer*)recognizer;
- (IBAction)handlePinch:(UIPinchGestureRecognizer*)sender;
- (IBAction)handleTap:(UITapGestureRecognizer*)sender;
- (IBAction)handleRotation:(UIRotationGestureRecognizer*)sender;

@end
