import pygame, csv, os
from constant import TILE_MAP_WIDTH, TILE_MAP_HEIGHT
from tile_checks import ground_floor_finder
class Tile(pygame.sprite.Sprite):
    def __init__(self, image, x, y, spritesheet):
        pygame.sprite.Sprite.__init__(self)
        self.image = spritesheet.parse_sprite(image)
        self.roundedNormalizedWidth, self.roundedNormalizedHeight = \
            (round(pygame.display.get_window_size()[0] / TILE_MAP_WIDTH),
             round(pygame.display.get_window_size()[1] / TILE_MAP_HEIGHT))
        self.image = pygame.transform.scale(self.image, (self.roundedNormalizedWidth, self.roundedNormalizedHeight))
        self.rect = self.image.get_rect()
        self.rect.x, self.rect.y = x, y

    def draw(self, surface):
        surface.blit(self.image, (self.rect.x, self.rect.y))

class TileMap():
    def __init__(self, filename, spritesheet):
        self.normalizedWidth, self.normalizedHeight = \
            (pygame.display.get_window_size()[0] / TILE_MAP_WIDTH),\
            (pygame.display.get_window_size()[1] / TILE_MAP_HEIGHT)
        self.tileWidth, self.tileHeight = (self.normalizedWidth, self.normalizedHeight)
        self.start_x, self.start_y = 0, 0
        self.spritesheet = spritesheet
        self.tiles, _ = self.load_tiles(filename)
        self.worldMap_surface = pygame.Surface((self.worldMap_w, self.worldMap_h))
        self.worldMap_surface.set_colorkey((0, 0, 0))
        self.load_worldMap()

    def draw_worldMap(self, surface):
        surface.blit(self.worldMap_surface, (0, 0))

    def load_worldMap(self):
        for tile in self.tiles:
            tile.draw(self.worldMap_surface)

    def read_csv(self, filename):
        worldMap = []
        with open(os.path.join(filename)) as data:
            data = csv.reader(data, delimiter=',')
            for row in data:
                worldMap.append(list(row))
        return worldMap

    def load_tiles(self, filename):
        tiles = []
        worldMap = self.read_csv(filename)
        nextItemDelim, nextRowDelim = 0, 0
        for worldMapRow in worldMap:
            nextItemDelim = 0
            for tile in worldMapRow:
                if "0" in tile:
                    self.start_x, self.start_y = nextItemDelim * self.tileWidth, nextRowDelim * self.tileHeight
                elif "2" in tile:
                    tiles.append(Tile("cavesofgallet35.png", nextItemDelim * self.tileWidth,
                                      nextRowDelim * self.tileHeight, self.spritesheet))
                elif "-6" in tile:
                    tiles.append(Tile("cavesofgallet1.png", nextItemDelim * self.tileWidth,
                                      nextRowDelim * self.tileHeight, self.spritesheet))
                nextItemDelim += 1

            # Move to next row
            nextRowDelim += 1
            # Store the size of the tile map
        self.worldMap_w, self.worldMap_h = nextItemDelim * self.tileWidth, nextRowDelim * self.tileHeight
        return tiles, worldMap
