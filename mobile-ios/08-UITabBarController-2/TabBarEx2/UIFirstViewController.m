//
//  UIFirstViewController.m
//  TabBarEx2
//
//  Created by exo on 2/13/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "UIFirstViewController.h"

@implementation UIFirstViewController
@synthesize label = _label;
static int viewCount = 0;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        myIndex = ++viewCount;
        NSString *viewName = [NSString stringWithFormat:@"View %d", myIndex];
        UITabBarItem *tabBarItem = [[UITabBarItem alloc] initWithTitle:viewName image:nil tag:0];
        if (myIndex == 1 || myIndex == 3) {
            tabBarItem.badgeValue = [NSString stringWithFormat:@"%d", myIndex];
        }
        self.tabBarItem = tabBarItem;
        [tabBarItem release];
     }
    return self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
 
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    self.label.text = [NSString stringWithFormat:@"View %d", myIndex];    
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [self setLabel:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)addMoreTab:(id)sender {
    UIViewController *aViewController = [[[UIFirstViewController alloc] initWithNibName:@"UIFirstViewController" bundle:nil] autorelease];
    NSMutableArray *controllers = [NSMutableArray arrayWithArray:self.tabBarController.viewControllers];
    [controllers addObject:aViewController];
    [self.tabBarController setViewControllers:controllers animated:YES];
    self.tabBarController.customizableViewControllers = [NSArray arrayWithObjects: nil];
        
}

- (void)dealloc {
    [_label release];
    [super dealloc];
}
@end
