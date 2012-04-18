//
//  ViewController.m
//  ActivityAndProgress
//
//  Created by exo on 2/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//
#import "AppDelegate.h"
#import "ViewController.h"
#import "PleaseWaitViewController.h"

@implementation ViewController
@synthesize myActivityView;
@synthesize myWaitView = _myWaitView;
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    self.myWaitView = [[[PleaseWaitViewController alloc] initWithNibName:@"PleaseWaitViewController" bundle:nil] autorelease];
    self.tabBarItem.title = @"Progress";
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setMyActivityView:nil];
    self.myWaitView = nil;
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
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
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (IBAction)doIt:(id)sender {
    if ([self.myActivityView isAnimating]) {
        [self.myActivityView stopAnimating];
    } else {
        [self.myActivityView startAnimating];
    }
}

int completed = 0;
- (void)moveBar:(id)sender {
    completed++;
    self.myWaitView.myProgress.progress = completed/20.0f;
    if (completed > 20) {
        [sender invalidate];
        [self.myWaitView.view removeFromSuperview];
        [self.view setAlpha:1.0f];
        completed = 0;
        self.myWaitView.myProgress.progress = 0;
    }
}

- (IBAction)doOther:(id)sender {
    self.myWaitView.view.backgroundColor = [UIColor clearColor];
    [self.view setAlpha:0.7f];
    UIWindow *window = ((AppDelegate*) [UIApplication sharedApplication].delegate).window;
    [window insertSubview:self.myWaitView.view aboveSubview:self.view];
    [NSTimer scheduledTimerWithTimeInterval:0.5 target:self selector:@selector(moveBar:) userInfo:nil repeats:YES];
}

- (void)dealloc {
    [myActivityView release];
    [_myWaitView release];
    [super dealloc];
}
@end
