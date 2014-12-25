//
//  NewClass.m
//  Variables
//
//  Created by Sergey Gomanyuk on 17.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "NewClass.h"

@implementation NewClass

@synthesize gramm;

- (void) tonnConvert: (int) Tn{
    int result = gramm/Tn;
    NSLog(@"%d грамм - это %d тн.", gramm, result );
}

- (void) ConvertationKilogramm: (int) kilogramm ConvertationTonn: (int) tonn Weight: (int) wgh{
    
    int resultKg = wgh/kilogramm;
    int resultTn = wgh/tonn ;
    NSLog(@"\n Вес в %d грамм - это %d килограмм. \n Но также %d грамм - это %d тн.", wgh, resultKg, wgh, resultTn);
    
}

@end
