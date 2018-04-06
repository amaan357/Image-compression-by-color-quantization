# Image-compression-by-color-quantization

-	Extracted the RGB values from each pixel of the input image to generate the required data for the model.
-	Clustered the data using user defined K-means library to reduce the size of the image.

|---------|        -----  Koala (Size = 762KB)  -------   |    ----  Penguins (Size = 759KB) ----    |
|---|---|---|

|   K values     |   Size(in KB)  | Compression ratio |  Size(in KB) | Compression ratio |
|----------------|----------------|-------------------|--------------|-------------------|
|        2       |       129      |        5.9        |       83     |        9.14       |
|        5       |       172      |        4.43       |       101    |        7.51       |
|        10      |       160      |        4.76       |       113    |        6.72       |
|        15      |       152      |        5.01       |       112    |        6.78       |
|        20      |       153      |        4.98       |       114    |        6.66       |



In general the higher the image quality, the lower the compression ratio

We try to find the best image quality for max compression ratio

By looking at the compression ratios we can say that for Koala image k = 15 is the best and for Penguins image also k = 15 is the best 
