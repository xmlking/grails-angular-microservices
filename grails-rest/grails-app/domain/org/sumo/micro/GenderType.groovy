package org.sumo.micro

enum GenderType {
    MALE('male'), FEMALE('female')

    final String value

    GenderType(String value) {
        this.value = value
    }

    String toString() {
        value
    }

    String getName() {
        name()
    }
}