package com.sep.backend_noAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OptRoutePOST {
    int[][] matrix;
    List<String> addresses;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OptRoutePOST{");
        sb.append("matrix=");
        if (matrix == null) {
            sb.append("null");
        } else {
            for (int i = 0; i < matrix.length; ++i) {
                sb.append(Arrays.toString(matrix[i]));
                if (i < matrix.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(", addresses=");
        sb.append(addresses);
        sb.append('}');
        return sb.toString();
    }
}
