package me.outspending.biomesapi.serialization;

import me.outspending.biomesapi.annotations.AsOf;

@AsOf("2.4.0")
public interface StringRepresentable {

    @AsOf("2.4.0")
    default String type() {
        return postProcess(StringRepresentable.asLowerSnakeCase(this.getClass().getSimpleName()));
    }

    default String postProcess(String processedClassName) {
        return processedClassName;
    }

    static String asLowerSnakeCase(String name) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char current = name.charAt(i);
            if (Character.isUpperCase(current)) {
                boolean hasPrevious = i > 0;
                boolean previousIsLower = hasPrevious && Character.isLowerCase(name.charAt(i - 1));
                boolean nextIsLower = i + 1 < name.length() && Character.isLowerCase(name.charAt(i + 1));
                boolean previousIsUpper = hasPrevious && Character.isUpperCase(name.charAt(i - 1));
                if (hasPrevious && (previousIsLower || (previousIsUpper && nextIsLower))) {
                    builder.append('_');
                }
                builder.append(Character.toLowerCase(current));
            } else {
                builder.append(current);
            }
        }
        return builder.toString();
    }
}