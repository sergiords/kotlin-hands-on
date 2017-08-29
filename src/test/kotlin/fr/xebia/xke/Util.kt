package fr.xebia.xke

import org.mockito.ArgumentCaptor

/**
 * Some sugar to create ArgumentCaptor<T>
 */
inline fun <reified T> captor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)
