//
//  Place+Flickr.h
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Place.h"

@interface Place (Flickr)

+ (Place*)placeWithName:(NSString *)name inManagedObjectContext:(NSManagedObjectContext*)context;

@end
