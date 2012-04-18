//
//  ViewController.m
//  Picnic
//
//  Created by exo on 3/9/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import <AudioToolbox/AudioToolbox.h>

@implementation ViewController
@synthesize basketTop;
@synthesize basketBottom;
@synthesize napkinTop;
@synthesize napkinBottom;
@synthesize bug;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - Animations
- (void)moveToLeft:(NSString *)animationID finished:(NSNumber *)finished context:(void *)context {
    if (bugDead) return;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelay:2.0];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    [UIView setAnimationDelegate:self];
    [UIView setAnimationDidStopSelector:@selector(faceRight:finished:context:)];
    bug.center = CGPointMake(75, 200);
    [UIView commitAnimations];
    
}

- (void)faceRight:(NSString *)animationID finished:(NSNumber *)finished context:(void *)context {
    if (bugDead) return;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelay:0.0];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    [UIView setAnimationDelegate:self];
    [UIView setAnimationDidStopSelector:@selector(moveToRight:finished:context:)];
    bug.transform = CGAffineTransformMakeRotation(M_PI);
    [UIView commitAnimations];
    
}

- (void)moveToRight:(NSString *)animationID finished:(NSNumber *)finished context:(void *)context {
    if (bugDead) return;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelay:2.0];
    [UIView setAnimationDelegate:self];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    [UIView setAnimationDidStopSelector:@selector(faceLeft:finished:context:)];
    bug.center = CGPointMake(230, 250);
    [UIView commitAnimations];
    
}

- (void)faceLeft:(NSString *)animationID finished:(NSNumber *)finished context:(void *)context {
    if (bugDead) return;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelay:0.0];
    [UIView setAnimationDelegate:self];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    [UIView setAnimationDidStopSelector:@selector(moveToLeft:finished:context:)];
    bug.transform = CGAffineTransformMakeRotation(0);
    [UIView commitAnimations];
    
}

- (void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    UITouch *touch = [touches anyObject];
    CGPoint touchLocation = [touch locationInView:self.view];
    
    CGRect bugRect = [[[bug layer] presentationLayer] frame];
    if (CGRectContainsPoint(bugRect, touchLocation)) {
        bugDead = true;
        [UIView animateWithDuration:0.7 delay:0.0 options:UIViewAnimationCurveEaseInOut animations:^{
            bug.transform = CGAffineTransformMakeScale(1.25, 0.75);
        }completion:^(BOOL finished) {
            [UIView animateWithDuration:2.0 delay:2.0 options:0 animations:^{
                bug.alpha = 0.0;
            } completion:^(BOOL finished) {
                [bug removeFromSuperview];
                self.bug = nil;
            }];
        }];
        NSString *squishPath = [[NSBundle mainBundle] pathForResource:@"squish" ofType:@"caf"];
        NSURL *url = [NSURL fileURLWithPath:squishPath];
        SystemSoundID systemSoundID;
        AudioServicesCreateSystemSoundID((CFURLRef) url, &systemSoundID);
        AudioServicesPlaySystemSound(systemSoundID);
    } else {
        NSLog(@"Bug not tapped.");
        return;
    }
    
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    CGRect basketTopFrame = self.basketTop.frame;
    basketTopFrame.origin.y = -basketTopFrame.size.height;
    
    CGRect basketBottomFrame = self.basketBottom.frame;
    basketBottomFrame.origin.y = self.view.bounds.origin.y + self.view.bounds.size.height;
    
    CGRect napkinTopFrame = self.napkinTop.frame;
    napkinTopFrame.origin.y = - napkinTopFrame.size.height;
    
    CGRect napkinBottomFrame = self.napkinBottom.frame;
    napkinBottomFrame.origin.y = self.view.bounds.origin.y + self.view.bounds.size.height;
    
    [UIView beginAnimations:nil context:nil];
    [UIView animateWithDuration:0.5 delay:1.0 options:UIViewAnimationCurveEaseInOut animations:^{
        basketTop.frame = basketTopFrame;
        basketBottom.frame = basketBottomFrame;
    } completion:^(BOOL finished) {
        NSLog(@"Animation done");
    }];
    [UIView animateWithDuration:0.7 delay:1.2 options:UIViewAnimationCurveEaseInOut animations:^{
        napkinTop.frame = napkinTopFrame;
        napkinBottom.frame = napkinBottomFrame;
    } completion:^(BOOL finished) {
        
    }];
    [UIView commitAnimations];
    [self moveToLeft:nil finished:nil context:nil];
}

- (void)viewDidUnload
{
    [self setBasketTop:nil];
    [self setBasketBottom:nil];
    [self setNapkinTop:nil];
    [self setNapkinBottom:nil];
    [self setBug:nil];
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)dealloc {
    [basketTop release];
    [basketBottom release];
    [napkinTop release];
    [napkinBottom release];
    [bug release];
    [super dealloc];
}
@end
