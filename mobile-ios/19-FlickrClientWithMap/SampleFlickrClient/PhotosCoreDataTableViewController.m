//
//  PhotoCoreDataTableViewController.m
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PhotosCoreDataTableViewController.h"
#import "Place+Flickr.h"
#import "Photo+Flickr.h"
#import "Tag.h"

@implementation PhotosCoreDataTableViewController

@synthesize place = _place;
@synthesize tag = _tag;
- (void)dealloc {
    [_place release];
    [_tag release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {

    }
    return self;
}

- (void)setupFetchedResultsControllerWithPlace:(Place*)place { // attaches an NSFetchRequest to this UITableViewController 
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"Photo"];
    request.sortDescriptors = [NSArray arrayWithObject:[NSSortDescriptor sortDescriptorWithKey:@"title" ascending:YES]];
    request.predicate = [NSPredicate predicateWithFormat:@"place.name = %@", place.name];
    
    self.debug = YES;
    self.fetchedResultsController = [[[NSFetchedResultsController alloc] initWithFetchRequest:request managedObjectContext:place.managedObjectContext sectionNameKeyPath:nil cacheName:nil] autorelease];
}

- (void)setupFetchedResultsControllerWithTag:(Tag*)tag {
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"Photo"];
    request.sortDescriptors = [NSArray arrayWithObject:[NSSortDescriptor sortDescriptorWithKey:@"title" ascending:YES]];
   request.predicate = [NSPredicate predicateWithFormat:@"ANY tags.name = %@", tag.name];
    
    self.debug = YES;
    self.fetchedResultsController = [[[NSFetchedResultsController alloc] initWithFetchRequest:request managedObjectContext:tag.managedObjectContext sectionNameKeyPath:nil cacheName:nil] autorelease];
}

- (void)setPlace:(Place *)place {
    [place retain];
    [_place release];
    _place = place;
    [self setupFetchedResultsControllerWithPlace:place];
}

- (void)setTag:(Tag *)tag {
    [tag retain];
    [_tag release];
    _tag = tag;
    [self setupFetchedResultsControllerWithTag:tag];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Photo Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] autorelease];
    }
    
    Photo *photo = [self.fetchedResultsController objectAtIndexPath:indexPath];
    
    cell.textLabel.text = photo.title;
    cell.detailTextLabel.text = photo.subTitle;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    //Photo *photo = [self.fetchedResultsController objectAtIndexPath:indexPath];
    
}

@end
