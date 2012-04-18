//
//  Photo+Flickr.h
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Photo.h"

@interface Photo (Flickr)

+ (Photo *)photoWithFlickrInfo:(NSDictionary *)flickrInfo inManagedObjectContext:(NSManagedObjectContext *)context;
+ (BOOL)photoIsExisted:(NSDictionary*)flickrInfo inManagedObjectContext:(NSManagedObjectContext *)context;

@end
