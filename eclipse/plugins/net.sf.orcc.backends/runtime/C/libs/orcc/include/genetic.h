/*
 * Copyright (c) 2010, IRISA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
#ifndef GENETIC_H
#define GENETIC_H

typedef struct actor_s actor_t;
typedef struct scheduler_s scheduler_t;
typedef struct sync_s sync_t;

typedef struct genetic_s {
	int population_size;
	int generation_nb;
	double keep_ratio;
	double crossover_ratio;
    actor_t **actors;
    scheduler_t *schedulers;
	int actors_nb;
	int threads_nb;
	int use_ring_topology;
    actor_t ***groups_of_actors;
	int *groups_size;
	int groups_nb;
	double groups_ratio;
} genetic_t;

typedef struct monitor_s {
    sync_t *sync;
    genetic_t *genetic_info;
} monitor_t;

typedef struct gene_s {
    actor_t *actor;
	int mapped_core;
} gene;

typedef struct individual_s {
	gene **genes;
	float fps;
	float old_fps;
} individual;

typedef struct population_s {
	int generation_nb;
	individual **individuals;
} population;

/**
 * Main routine of the genetic algorithm.
 */
void *monitor(void *data);

/**
 * Initialize the given genetic structure.
 */
void genetic_init(genetic_t *genetic_info, int population_size,
		int generation_nb, double keep_ratio, double crossover_ratio,
        actor_t **actors, scheduler_t *schedulers, int actors_nb,
		int threads_nb, int use_ring_topology, int groups_nb,
		double groups_ratio);

/**
 * Initialize the given monitor structure.
 */
void monitor_init(monitor_t *monitoring, sync_t *sync, genetic_t *genetic_info);

/**
 * Allocate and read a parameter-sized table to clean processor cache.
 */
int clean_cache(int size);

// Display functions
extern float computePartialFps();
extern void backupPartialStartInfo();
extern void backupPartialEndInfo();
extern void active_fps_printing();
extern void remove_fps_printing();
extern void display_close();

// Source functions
extern void source_activeGenetic();
extern void source_close();

// Application functions
extern void initialize_instances();
extern void clear_fifos();
extern int is_timeout();

#endif