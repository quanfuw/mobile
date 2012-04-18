//
//  RecentPhotosManagement.h
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define PHOTOS_SIZE 20

@interface RecentPhotosManagement : NSObject

+ (void)declarePrefsIfNotExist;
+ (void)pushPhotoToList:(NSDictionary*)photo;
+ (NSArray*)getRecentPhotos;

@end
