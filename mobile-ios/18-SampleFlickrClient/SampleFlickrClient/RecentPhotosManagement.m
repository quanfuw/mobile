//
//  RecentPhotosManagement.m
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "RecentPhotosManagement.h"

@implementation RecentPhotosManagement

static NSString *RECENT_PHOTOS = @"recent_photo";

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


@end
