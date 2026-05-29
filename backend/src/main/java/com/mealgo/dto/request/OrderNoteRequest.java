package com.mealgo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderNoteRequest {

    @Size(max = 200, message = "備註不可超過200字")
    private String orderNote;
}
