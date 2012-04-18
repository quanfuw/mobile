//
//  TagCoreDataTableViewController.m
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "TagsCoreDataTableViewController.h"
#import "VacationHelper.h"
#import "Tag.h"
#import "PhotosCoreDataTableViewController.h"

@implementation TagsCoreDataTableViewController

@synthesize vacationName = _vacationName;

- (void)dealloc {
    [_vacationName release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)setupFetchedResultsController:(NSManagedObjectContext*)context { // attaches an NSFetchRequest to this UITableViewController 
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"Tag"];
    request.sortDescriptors = [NSArray arrayWithObject:[NSSortDescriptor sortDescriptorWithKey:@"photosNumber" ascending:NO]];
    
    self.debug = YES;
    self.fetchedResultsController = [[[NSFetchedResultsController alloc] initWithFetchRequest:request managedObjectContext:context sectionNameKeyPath:nil cacheName:nil] autorelease];
}

#pragma mark - setter & getter


- (void)setVacationName:(NSString *)vacationName {
    [_vacationName release];
    [vacationName retain];
    _vacationName = vacationName;
    [VacationHelper openVacation:vacationName usingBlock:^(UIManagedDocument *document) {
        [self setupFetchedResultsController:document.managedObjectContext];
    }];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Tag Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    Tag *tag = [self.fetchedResultsController objectAtIndexPath:indexPath];
    
    cell.textLabel.text = [tag.name capitalizedString];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    Tag *tag = [self.fetchedResultsController objectAtIndexPath:indexPath];
    PhotosCoreDataTableViewController *photosController = [[[PhotosCoreDataTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
    photosController.tag = tag;
    [self.navigationController pushViewController:photosController animated:YES];
}

@end
