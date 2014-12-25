//
//  XYZShoutingPerson.m
//  LanguageLearning
//
//  Created by Sergey Gomanyuk on 10.10.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "XYZShoutingPerson.h"

@implementation XYZShoutingPerson

-(void)saySomething:(NSString *)something {
    [super saySomething:[something uppercaseString]];
}

@end
