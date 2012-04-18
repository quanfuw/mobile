//
//  Tag+Flickr.h
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Tag.h"

@interface Tag (Flickr)
+ (Tag*)tagWithName:(NSString *)name inManagedObjectContext:(NSManagedObjectContext*)context;
@end
