package com.bluebox.planner.auth.common.viewModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kamran ghiasvand
 */
@NoArgsConstructor
@Getter
@Setter
public class CustomPage {
    private Integer start = 0;
    private Integer size = 50;

    public CustomPage(Integer start, Integer size) {
        this.start = start;
        this.size = size;
    }
}
