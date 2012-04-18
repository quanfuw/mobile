//
//  RecentPhotosManagement.m
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "CacheManager.h"

@implementation CacheManager

static NSString *const RECENT_PHOTOS = @"recent_photo";
static NSString *const IMAGE_CACHE_DIR = @"ImageCache";

+ (NSString *)getBaseCachePath {
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    return [[array objectAtIndex:0] stringByAppendingPathComponent:IMAGE_CACHE_DIR];
}

+ (void)declarePrefsIfNotExist {
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    if ([prefs objectForKey:RECENT_PHOTOS] == nil) {
        [prefs registerDefaults:[NSDictionary dictionaryWithObject:[NSArray array] forKey:RECENT_PHOTOS]];
    }
}

+ (void)pushPhotoToList:(NSDictionary *)photo {
    NSString *idString = [photo objectForKey:@"id"];
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    NSArray *photos = [prefs objectForKey:RECENT_PHOTOS];
    NSMutableArray *newArray = [NSMutableArray arrayWithCapacity:PHOTOS_SIZE];
    [newArray addObject:photo];
    for (int i = 1; i < MIN(PHOTOS_SIZE, [photos count] + 1); i++) {
        NSDictionary *aPhoto = [photos objectAtIndex:(i - 1)];
        NSString *aPhotoId = [aPhoto objectForKey:@"id"];
        if (![idString isEqualToString:aPhotoId]) {
            [newArray addObject:aPhoto];
        }
    }
    [prefs setObject:newArray forKey:RECENT_PHOTOS];
    [prefs synchronize];
}

+ (NSArray *)getRecentPhotos {
    return [[NSUserDefaults standardUserDefaults] objectForKey:RECENT_PHOTOS];
}


#pragma mark - image cache 

+ (void)initCache:(NSError*)error {
    NSString *dataPath = [CacheManager getBaseCachePath];
    /* check for existence of cache directory */
	if ([[NSFileManager defaultManager] fileExistsAtPath:dataPath]) {
		return;
	}
    /* create a new cache directory */
	if (![[NSFileManager defaultManager] createDirectoryAtPath:dataPath
								   withIntermediateDirectories:NO
													attributes:nil
														 error:&error]) {
		return;
	}
}

+ (void)clearCache:(NSError*)error {
    NSString *dataPath = [CacheManager getBaseCachePath];
    /* remove the cache directory and its contents */
	if (![[NSFileManager defaultManager] removeItemAtPath:dataPath error:&error]) {
		return;
	}
	[CacheManager initCache:error];
}


+ (void)cacheImage:(NSData *)image imageId:(NSString *)imageId error:(NSError *)error{
    NSString *dataPath = [CacheManager getBaseCachePath];
    NSString *filePath = [dataPath stringByAppendingPathComponent:imageId];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    long cacheSize = [CacheManager getCacheSize:error];
    long maxCacheSize = CACHE_SIZE * 1024 * 1024;
    long imageSize = [image length];
    
    if (![fileManager fileExistsAtPath:filePath] && image) {
        if ((cacheSize + imageSize) <= maxCacheSize) {
            [fileManager createFileAtPath:filePath contents:image attributes:nil];            
        } else {
            // if cache size is exceeded, remove the oldest cache item
            do {
                NSString *oldestItemPath = [CacheManager getOldestCacheItemPath:error];
                [fileManager removeItemAtPath:oldestItemPath error:&error];
                cacheSize = [CacheManager getCacheSize:error];
            } while (cacheSize + imageSize > maxCacheSize);
            // cache new one
            [fileManager createFileAtPath:filePath contents:image attributes:nil];            
        }
    }
    
    
}

+ (NSString*)getImageCachePath:(NSString *)imageId {
    NSString *dataPath = [CacheManager getBaseCachePath];
    NSString *filePath = [dataPath stringByAppendingPathComponent:imageId];
    if ([[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
        return filePath;
    }
    return nil;
}

+ (unsigned long long)getCacheSize:(NSError *)error {
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSString *basePath = [CacheManager getBaseCachePath];
    NSArray *array = [fileManager subpathsAtPath:basePath];
    NSEnumerator *enumulator = [array objectEnumerator];
    NSString *fileName;
    unsigned long long int cacheSize = 0;
    while (fileName = [enumulator nextObject]) {
        // explore all files...
        NSDictionary *fileAttributes = [fileManager attributesOfItemAtPath:[basePath stringByAppendingPathComponent:fileName] error:&error];
        cacheSize += [fileAttributes fileSize];
    }
    return cacheSize;
}

+ (NSString *)getOldestCacheItemPath:(NSError *)error {
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSString *basePath = [CacheManager getBaseCachePath];
    NSArray *array = [fileManager subpathsAtPath:basePath];
    NSEnumerator *enumulator = [array objectEnumerator];
    NSString *oldestItemPath = nil;
    NSDate *lastUpdateDate = nil;
    NSString *fileName;
    while (fileName = [enumulator nextObject]) {
        NSDictionary *fileAttributes = [fileManager attributesOfItemAtPath:[basePath stringByAppendingPathComponent:fileName] error:&error];
        NSDate *mdfDate = [fileAttributes fileModificationDate];
        if (!lastUpdateDate) {
            lastUpdateDate = mdfDate;
            oldestItemPath = [basePath stringByAppendingPathComponent:fileName];
        } else {
            if ([lastUpdateDate compare:mdfDate] == NSOrderedDescending) {
                // compare modification date
                lastUpdateDate = mdfDate;
                oldestItemPath = [basePath stringByAppendingPathComponent:fileName];
            }
        }
            
        
    }
    return oldestItemPath;
}

@end
