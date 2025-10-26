package com.paradigma.filmio.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserSocialStatistics implements SocialStatistics{
    private long followers;
    private long following;
}
