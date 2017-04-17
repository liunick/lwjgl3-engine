# lwjgl3-engine

Following ThinMatrix's engine tutorial found here: https://www.youtube.com/watch?v=VS8wlS9hF8E&amp;list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP

LWJGL is a java implementation of OpenGL, OpenCV, OpenAL and other libraries natively written in C. 

ThinMatrix uses a deprecated version of LWJGL (LWJGL 2). This project follows loosely along with his tutorial but recreates his engine in LWJGL 3, the most updated version of the library. The majority of changes found in LWJGL 3 are due to updating to more concise/optimized OpenGL functions. 

Differences between LWJGL 2 and LWJGL 3 discovered so far:
- No more Display class -> Instead adopts GLFW
- No more Vector/Matrix classes -> Created my own Vector/Matrix classes
