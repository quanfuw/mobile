//
//  TimerViewController.m
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "TimerViewController.h"

@implementation TimerViewController
@synthesize startButton;
@synthesize myPicker;
@synthesize label;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.title = @"Timer";
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [self setMyPicker:nil];
    [self setLabel:nil];
    [self setStartButton:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)dealloc {
    [myPicker release];
    [label release];
    [startButton release];
    [super dealloc];
}
NSInteger seconds = 0;
- (IBAction)echoTime:(id)sender {
    NSDate *time = self.myPicker.date;
    NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease];
    [dateFormatter setDateFormat:@"HH:MM:SS"];
    NSCalendar *gregorian = [[NSCalendar alloc] initWithCalendarIdentifier:NSGregorianCalendar];
    NSDateComponents *comps = [gregorian components:(NSHourCalendarUnit | NSMinuteCalendarUnit) fromDate:time];
    [gregorian release];
    NSInteger hour = [comps hour];
    NSInteger min = [comps minute];
    NSInteger secs = hour * 60 * 60 + min * 60;
    NSNumber * elapsedSeconds = [[[NSNumber alloc] initWithInt:secs] autorelease];
    NSDictionary * myDict = [NSDictionary dictionaryWithObject:
                             elapsedSeconds forKey:@"TotalSeconds"];
    [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(echoIt:) userInfo:myDict repeats:YES];
    self.startButton.enabled = NO;
    
}

- (void)echoIt:(NSTimer*)timer {
    if ([timer.userInfo isKindOfClass:[NSDictionary class]]) {
        NSNumber * num = (NSNumber *) [[timer userInfo] valueForKey:@"TotalSeconds"];
        seconds++;
        NSInteger secs = [num integerValue] - seconds;
        if (secs <= 0) {
            [timer invalidate];
            seconds = 0;
            self.label.text = @"Finished";
            self.startButton.enabled = YES;
            return;
        }
        self.label.text = [NSString stringWithFormat:@"Elapse: %i - remaining: %i", seconds, secs];        
    } else {
        self.startButton.enabled = YES;
        [timer invalidate];
    }
}

@end
