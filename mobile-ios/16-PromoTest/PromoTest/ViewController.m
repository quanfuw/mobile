//
//  ViewController.m
//  PromoTest
//
//  Created by exo on 2/22/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "SVProgressHUD.h"

@implementation ViewController
@synthesize textField;
@synthesize textView;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setTextField:nil];
    [self setTextView:nil];
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
    return YES;
}

#pragma mark - implement UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)aTextField {
    [self.textField resignFirstResponder];
    // get Device ID
    NSString* uniqueID = [[UIDevice currentDevice] uniqueIdentifier];
    NSString *code = aTextField.text;
    NSURL *url = [NSURL URLWithString:@"http://www.wildfables.com/promos/"];
    ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:url];
    [request setPostValue:@"1" forKey:@"rw_app_id"];
    [request setPostValue:code forKey:@"code"];
    [request setPostValue:uniqueID forKey:@"device_id"];
    request.delegate = self;
    [request startAsynchronous];
    [SVProgressHUD showWithStatus:@"Redeeming code..." maskType:SVProgressHUDMaskTypeGradient];
    textField.text = @"";
    return YES;
}

#pragma mark - implement ASIHTTPRequestDelegate
- (void)requestFinished:(ASIHTTPRequest *)request {
    if (request.responseStatusCode == 400) {
        textView.text = @"Invalid code";        
    } else if (request.responseStatusCode == 403) {
        textView.text = @"Code already used";
    } else if (request.responseStatusCode == 200) {
        NSString *responseString = [request responseString];
        NSDictionary *responseDict = [responseString JSONValue];
        NSString *unlockCode = [responseDict objectForKey:@"unlock_code"];
        
        if ([unlockCode compare:@"com.razeware.test.unlock.cake"] == NSOrderedSame) {
            textView.text = @"The cake is a lie!";
        } else {        
            textView.text = [NSString stringWithFormat:@"Received unexpected unlock code: %@", unlockCode];
        }
        
    } else {
        textView.text = @"Unexpected error";
    }
    [SVProgressHUD dismiss];
}

- (void)dealloc {
    [textField release];
    [textView release];
    [super dealloc];
}

@end
