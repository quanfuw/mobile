//
//  ButtonsViewController.m
//  Controls
//
//  Created by exo on 2/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ButtonsViewController.h"

@implementation ButtonsViewController

@synthesize grayButton = _grayButton, imageButton = _imageButton, roundButton = _roundButton;

- (void)dealloc {
    [_grayButton release];
    [_imageButton release];
    [_roundButton release];
    [super dealloc];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
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
    [self.view addSubview:self.grayButton];
    [self.view addSubview:self.roundButton];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    [_grayButton release];
    _grayButton = nil;
    [_roundButton release];
    _roundButton = nil;
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - buttons
#define MyButtonHeight 40.0
#define MyButtonWidth 106.0

- (void)action:(id)sender {
    
}

+ (UIButton *)newButtonWithTitle:(NSString *)title target:(id)target selector:(SEL)selector frame:(CGRect)frame image:(UIImage *)image imagePressed:(UIImage *)imagePressed darkTextColor:(BOOL)darkTextColor {
    UIButton *button = [[[UIButton alloc] initWithFrame:frame] autorelease];
    button.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;
    button.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
    [button setTitle:title forState:UIControlStateNormal];
    if (darkTextColor) {
        [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    } else {
        [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    }
    UIImage *newImage = [image stretchableImageWithLeftCapWidth:12.0 topCapHeight:0.0];
	[button setBackgroundImage:newImage forState:UIControlStateNormal];
	
	UIImage *newPressedImage = [imagePressed stretchableImageWithLeftCapWidth:12.0 topCapHeight:0.0];
	[button setBackgroundImage:newPressedImage forState:UIControlStateHighlighted];
	
	[button addTarget:target action:selector forControlEvents:UIControlEventTouchUpInside];
	
    // in case the parent view draws with a custom color or gradient, use a transparent color
	button.backgroundColor = [UIColor clearColor];
    return button;
}

- (UIButton *)grayButton {
    if (!_grayButton) {
        CGRect frame = CGRectMake(150.0, 5.0, MyButtonWidth, MyButtonHeight);
        UIImage *image = [UIImage imageNamed:@"whiteButton.png"];
        UIImage *pressedImage = [UIImage imageNamed:@"blueButton.png"];
        _grayButton = [ButtonsViewController newButtonWithTitle:@"Gray Button" target:self selector:@selector(action:) frame:frame image:image imagePressed:pressedImage darkTextColor:YES];
        [_grayButton retain];
    }
    return _grayButton;
}

- (UIButton *)roundButton {
    if (!_roundButton) {
        _roundButton = [[UIButton buttonWithType:UIButtonTypeRoundedRect] retain];
        _roundButton.frame = CGRectMake(150.0, 100.0, MyButtonWidth, MyButtonHeight);
        [_roundButton setTitle:@"Round" forState:UIControlStateNormal];
        _roundButton.backgroundColor = [UIColor clearColor];
        [_roundButton addTarget:self action:@selector(action:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _roundButton;
}

@end
