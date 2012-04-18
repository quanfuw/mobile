//
//  MainViewController.m
//  Controls
//
//  Created by exo on 2/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MainViewController.h"
#import "ButtonsViewController.h"
#import "ToolbarViewController.h"
#import "SwitchSliderViewController.h"
#import "TextFieldViewController.h"
#import "SegmentViewController.h"
#import "MyWebViewController.h"

@implementation MainViewController

@synthesize menuList = _menuList;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {

    }
    return self;
}

- (void)dealloc {
    [_menuList release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

static NSString *TITLE = @"title";
static NSString *CONTROLLER = @"controller";

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.menuList = [NSMutableArray array];
    UIViewController *buttonController = [[[ButtonsViewController alloc] initWithNibName:@"ButtonsViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Button", TITLE, buttonController, CONTROLLER, nil]];
    UIViewController *toolbarController = [[[ToolbarViewController alloc] initWithNibName:@"ToolbarViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Toolbar", TITLE, toolbarController, CONTROLLER, nil]];
    UIViewController *switchController = [[[SwitchSliderViewController alloc] initWithNibName:@"SwitchSliderViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Switch & Slider", TITLE, switchController, CONTROLLER, nil]];
    UIViewController *textfieldController = [[[TextFieldViewController alloc] initWithNibName:@"TextFieldViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Textfield", TITLE, textfieldController, CONTROLLER, nil]];
    UIViewController *segmentViewController = [[[SegmentViewController alloc] initWithNibName:@"SegmentViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Segment List", TITLE, segmentViewController, CONTROLLER, nil]];
    UIViewController *myWebViewController = [[[MyWebViewController alloc] initWithNibName:@"MyWebViewController" bundle:nil] autorelease];
    [self.menuList addObject:[NSDictionary dictionaryWithObjectsAndKeys:@"Web view", TITLE, myWebViewController, CONTROLLER, nil]];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.menuList = nil;
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
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.menuList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    cell.textLabel.text = [[self.menuList objectAtIndex:indexPath.row] valueForKey:TITLE];
    cell.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;
    
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.navigationController pushViewController:[[self.menuList objectAtIndex:indexPath.row] valueForKey:CONTROLLER] animated:YES];
}

@end
