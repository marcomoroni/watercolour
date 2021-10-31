// https://www.reddit.com/r/proceduralgeneration/comments/6mta0f/watercolor_simple_noise_algorithm/

void setup()
{
  size(700, 700);
  background(100);
  colorMode(RGB, 255, 255, 255, 1);

  // Generate 10 images
  for (int image = 0; image < 10; image++)
  {
    // Seed
    long seed = System.nanoTime();
    randomSeed(seed);

    // Staring size
    int size = 3;

    // The pixels
    color[][] pxs = new color[size][size];

    // Initialize with random colours
    for (int x = 0; x < size; x++)
    {
      for (int y = 0; y < size; y++)
      {
        pxs[x][y] = color(random(255), random(255), random(255));
      }
    }

    // Zoom iterations
    int zoom = 8;

    // Zoom
    for (int z = 0; z < zoom; z++)
    {
      // Expand size
      size = (size * 2) - 1;

      // Create new array of zoomed pixels
      color[][] zoomedPxs = new color[size][size];

      // Set colours by 2x2 pixels chunks, with the top left of the original colour
      for (int x = 0; x < size; x += 2)
      {
        for (int y = 0; y < size; y += 2)
        {
          // Top left is the original
          zoomedPxs[x][y] = pxs[x / 2][y / 2];

          // Top right
          if (x < size - 1)
          {
            float r = random(1);
            zoomedPxs[x + 1][y] = lerpColor(pxs[x / 2][y / 2], pxs[x / 2 + 1][y / 2], r);
          }

          // Bottom left
          if (y < size - 1)
          {
            float r = random(1);
            zoomedPxs[x][y + 1] = lerpColor(pxs[x / 2][y / 2], pxs[x / 2][y / 2 + 1], r);
          }

          // Bottom right
          if (x < size - 1 && y < size - 1)
          {
            float r1 = random(1);
            float r2 = random(1);
            color c1 = lerpColor(pxs[x / 2][y / 2], pxs[x / 2 + 1][y / 2 + 1], r1);
            color c2 = lerpColor(pxs[x / 2][y / 2 + 1], pxs[x / 2 + 1][y / 2], r2);
            float r3 = random(1);
            zoomedPxs[x + 1][y + 1] = lerpColor(c1, c2, r3);
          }
        }
      }

      // Save zoomed pixels
      pxs = zoomedPxs;
    }

    print(pxs);

    save("output/watercolor_" + seed + ".tif");
  }
}

void print(color[][] pxs)
{
  int side = ceil((float)width / pxs.length);
  noStroke();
  for (int x = 0; x < pxs.length; x++)
  {
    for (int y = 0; y < pxs.length; y++)
    {
      for (int sx = 0; sx < side; sx++)
      {
        for (int sy = 0; sy < side; sy++)
        {
          set(side * x + sx, side * y + sy, pxs[x][y]);
        }
      }
    }
  }
}
