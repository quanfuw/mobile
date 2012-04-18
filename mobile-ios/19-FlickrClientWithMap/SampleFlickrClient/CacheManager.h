//
//  RecentPhotosManagement.h
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define PHOTOS_SIZE 20
// cache size: 10MB
#define CACHE_SIZE 10

@interface CacheManager : NSObject

+ (void)declarePrefsIfNotExist;
+ (void)pushPhotoToList:(NSDictionary*)photo;
+ (NSArray*)getRecentPhotos;

+ (void)cacheImage:(NSData*)image imageId:(NSString*)imageId error:(NSError*)error;
+ (NSString*)getImageCachePath:(NSString*)imageId;
+ (void)initCache:(NSError*)error;
+ (void)clearCache:(NSError*)error;
+ (NSString*)getBaseCachePath;
+ (unsigned long long)getCacheSize:(NSError*)error;
+ (NSString*)getOldestCacheItemPath:(NSError*)error;

@end
