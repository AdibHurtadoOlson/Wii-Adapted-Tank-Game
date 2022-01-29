import pygame
from constant import TILE_MAP_WIDTH, TILE_MAP_HEIGHT, GRAVITY, PLAYER_SPRITESHEET_SIZES, PLAYER_IDLE,\
    PLAYER_ATTACK, PLAYER_DEATH, PLAYER_TAKEN_DAMAGE, PLAYER_RUN, ANIMATION_COOLDOWN, GAME_LEVEL, TILE_CORNER_NUMBER
from spritesheet import Spritesheet
from tiles import TileMap
from sprite_constant import mapSpritesheet
import math


class Player(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.flip = False
        self.direction = 1
        self.velocity = 4
        self.velY = 4
        self.falling = False
        self.windowWidth, self.windowHeight = pygame.display.get_window_size()
        self.alive = True
        self.jump = False
        self.inAir = True
        self.animationList = []
        self.frameIndex = 0
        self.action = 0
        self.updateTime = pygame.time.get_ticks()
        self.moveSpritesheet = \
            Spritesheet("C:/Users/adibh/PycharmProjects/Tilemap-Test-02/assets/Evil Wizard/Move/Move.png")
        self.attackSpritesheet = \
            Spritesheet("C:/Users/adibh/PycharmProjects/Tilemap-Test-02/assets/Evil Wizard/Attack/Attack.png")
        self.idleSpritesheet = \
            Spritesheet("C:/Users/adibh/PycharmProjects/Tilemap-Test-02/assets/Evil Wizard/Idle/Idle.png")
        self.deathSpritesheet = \
            Spritesheet("C:/Users/adibh/PycharmProjects/Tilemap-Test-02/assets/Evil Wizard/Death/Death.png")
        self.takeHitSpritesheet = \
            Spritesheet("C:/Users/adibh/PycharmProjects/Tilemap-Test-02/assets/Evil Wizard/Take Hit/Take Hit.png")
        self.animationTypes = \
            [self.moveSpritesheet,
             self.idleSpritesheet,
             self.attackSpritesheet,
             self.takeHitSpritesheet,
             self.deathSpritesheet]

        for i in range(len(self.animationTypes)):
            tempList = []
            for x in range(PLAYER_SPRITESHEET_SIZES[i]):
                img = self.animationTypes[i].parse_sprite(f'{x}.png')
                tempList.append(img)

            self.animationList.append(tempList)

        self.image = self.animationList[self.action][self.frameIndex]
        self.rect = self.image.get_rect()

    def movement(self):
        keys = pygame.key.get_pressed()
        if self.alive:

            if keys[pygame.K_a]:
                self.x -= self.velocity
                self.flip = True
                self.update_action(PLAYER_RUN)

            if keys[pygame.K_d]:
                self.flip = False
                self.update_action(PLAYER_RUN)
                self.x += self.velocity

            if keys[pygame.K_w]:
                self.update_action(PLAYER_DEATH)
                self.y -= self.velY
                self.inAir = True

            if keys[pygame.K_s]:
                if self.rect.bottom < gameFloor:
                    if gameFloor - self.rect.bottom > self.velY:
                        self.update_action(PLAYER_DEATH)
                        self.y += self.velY
                    else:
                        self.y += gameFloor - self.rect.bottom

                elif self.rect.bottom > gameFloor:
                    self.y = gameFloor - self.rect.height
                    print("rect.bottom > groundFloor:", self.rect.bottom, gameFloor)

                else:
                    pass

                if self.rect.bottom == gameFloor:
                    self.update_action(PLAYER_ATTACK) # Set to crouch animation later

            if self.y + self.rect.h == gameFloor:
                self.inAir = False

            if sum(keys) is 0:
                self.update_action(PLAYER_IDLE)

    def update_animation(self):
        self.image = self.animationList[self.action][self.frameIndex]
        if pygame.time.get_ticks() - self.updateTime > ANIMATION_COOLDOWN:
            self.updateTime = pygame.time.get_ticks()
            self.frameIndex += 1

        if self.frameIndex >= len(self.animationList[self.action]):
            self.frameIndex = 0

    def update_action(self, newAction):
        if newAction != self.action:
            self.action = newAction
            self.frameIndex = 0
            self.updateTime = pygame.time.get_ticks()

    def draw(self, window):
        self.image = pygame.transform.scale(self.image,
                                            (round(pygame.display.get_window_size()[0] / TILE_MAP_WIDTH),
                                             round(pygame.display.get_window_size()[1] / TILE_MAP_HEIGHT)))
        self.x, self.y = self.x * (pygame.display.get_window_size()[0] / self.windowWidth), \
                         self.y * (pygame.display.get_window_size()[1] / self.windowHeight)
        self.rect.x, self.rect.y = self.x, self.y
        self.windowWidth, self.windowHeight = pygame.display.get_window_size()
        window.blit(pygame.transform.flip(self.image, self.flip, False),
                    (self.rect.x, self.rect.y, self.rect.w, self.rect.h))