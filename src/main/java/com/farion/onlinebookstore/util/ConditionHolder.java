package com.farion.onlinebookstore.util;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ConditionHolder {
    private Set<Long> condition = new HashSet<>();
}
