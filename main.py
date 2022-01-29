from tiles import *
import pygame
from constant import GAME_LEVEL
from spritesheet import Spritesheet


# LOAD UP A BASIC WINDOW AND CLOCK #
pygame.init()
DISPLAY_W, DISPLAY_H = 1028, 720
canvas = pygame.Surface((DISPLAY_W,DISPLAY_H))
window = pygame.display.set_mode(((DISPLAY_W,DISPLAY_H)), pygame.RESIZABLE)
running = True
clock = pygame.time.Clock()

# LOAD SPRITES | LOAD MAP | LOAD GAME WINDOW #
from player import Player
from sprite_constant import mapSpritesheet
worldMap = TileMap(GAME_LEVEL, mapSpritesheet)
playerX, playerY = worldMap.start_x, worldMap.start_y
tileGroup = pygame.sprite.Group()
for tile in worldMap:
    tileGroup.add(tile)
player = Player(playerX, playerY)
playerGroup = pygame.sprite.Group()
playerGroup.add(player);
windowSize = pygame.display.get_window_size()
windowResizeNeeded = False

def redraw_game_window():
    canvas.fill((0, 180, 240))  # Fills the entire screen with light blue
    worldMap.draw_worldMap(canvas)
    window.blit(canvas, (0, 0))
    player.draw(window)
    player.update_animation()
    pygame.display.update()


# GAME LOOP #
while running:
    clock.tick(60)

    if windowSize != pygame.display.get_window_size():
        windowResizeNeeded = True

    if windowResizeNeeded:
        canvas = pygame.Surface(pygame.display.get_window_size())
        window = pygame.display.set_mode(pygame.display.get_window_size(), pygame.RESIZABLE)
        worldMap = TileMap(GAME_LEVEL, mapSpritesheet)
        windowSize = pygame.display.get_window_size()
        player.find_position_on_world_floor()
        windowResizeNeeded = False

    # CHECK PLAYER INPUT #
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False


    # Collision Detection #
    if pygame.sprite.groupcollide(playerGroup, tileGroup, False):


    # UPDATE WINDOW AND DISPLAY #
    player.movement()
    redraw_game_window()
