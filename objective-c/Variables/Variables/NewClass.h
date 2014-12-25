//
//  NewClass.h
//  Variables
//
//  Created by Sergey Gomanyuk on 17.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NewClass : NSObject

@property (nonatomic) int gramm;
- (void) tonnConvert: (int) Tn;
- (void) ConvertationKilogramm: (int) kilogramm ConvertationTonn: (int) tonn Weight: (int) wgh;

@end
