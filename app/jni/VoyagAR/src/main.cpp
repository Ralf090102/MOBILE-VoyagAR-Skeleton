#include <SDL.h>

int main(int argc, char *argv[])
{
    if (SDL_Init(SDL_INIT_EVENTS | SDL_INIT_VIDEO) != 0) {
        SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "SDL_Init failed (%s)", SDL_GetError());
    }

    if (SDL_ShowSimpleMessageBox(SDL_MESSAGEBOX_INFORMATION, "Anito VoyagAR Mobile",
                                 "NO SHOT DID IT ACTUALLY WORK!??", NULL) != 0) {
        SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "SDL_ShowSimpleMessageBox failed (%s)", SDL_GetError());
    }

    return 0;
}
