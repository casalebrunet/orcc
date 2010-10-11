// /TestFifo/data_src
// Source file is "/home/moot/orcc/trunk/test/Pass/VTL/Fifo/DataSource.cal"

#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"
////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
////////////////////////////////////////////////////////////////////////////////
// Output FIFOs
extern struct fifo_i32_s *data_src_O;


////////////////////////////////////////////////////////////////////////////////
// Parameter values of the instance
#define NUM 300000000

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor
static i32 count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures
////////////////////////////////////////////////////////////////////////////////
// Actions

static void done(struct fifo_i32_s *fifo) {
	i32 O[1];

	O[0] = -1;
	fifo->contents[fifo->write_ind] = O[0];
	fifo->write_ind++;
}



static i32 isSchedulable_done() {
	i32 local_count_1;
	i32 local_NUM_1;
	i32 result_1;

	local_count_1 = count;
	local_NUM_1 = NUM;
	result_1 = local_count_1 == local_NUM_1;
	return result_1;
}



static void loop(struct fifo_i32_s *fifo) {
	i32 O[1];
	i32 local_count_1;
	i32 current_1;
	i32 local_count_2;

	local_count_1 = count;
	current_1 = local_count_1;
	local_count_2 = local_count_1 + 1;
	O[0] = current_1;
	fifo->contents[fifo->write_ind] = O[0];
	fifo->write_ind++;
	count = local_count_2;
}



static i32 isSchedulable_loop() {
	i32 result_1;

	result_1 = 1;
	return result_1;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler
enum states {
	s_s0 = 0,
	s_s1
};

static char *stateNames[] = {
	"s_s0",
	"s_s1"
};

static enum states _FSM_state = s_s0;

void data_src_scheduler(struct schedinfo_s *si) {
	int i = 0;

	int max_ind;

	int read_ind_I = data_src_O->read_ind;
	int write_ind_I = data_src_O->write_ind;
	int size = data_src_O->size;

	if (read_ind_I < write_ind_I) {
		data_src_O->write_ind = read_ind_I;
		max_ind = write_ind_I;
	} else {
		if (write_ind_I == size) {
			data_src_O->write_ind = 0;
			data_src_O->read_ind = 0;
		} else {
			data_src_O->write_ind = write_ind_I;
		}
		max_ind = size;
	}

	// Visual: _mm_prefetch(&contents[min_ind], _MM_HINT_NTA);
	// __builtin_prefetch(&contents[min_ind], 1, 3);

	// jump to FSM state 
	switch (_FSM_state) {
	case s_s0:
		goto l_s0;
	case s_s1:
		goto l_s1;
	default:
		printf("unknown state: %s\n", stateNames[_FSM_state]);
	}

	// FSM transitions

l_s0:
	if (isSchedulable_done()) {
		int ports = 0;
		if (!(data_src_O->write_ind < max_ind)) {
			ports |= 0x01;
		}
		if (ports != 0) {
			_FSM_state = s_s0;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;

			return;
		}
		done(data_src_O);
		i++;
		goto l_s1;
	} else if (isSchedulable_loop()) {
		int ports = 0;
		if (!(data_src_O->write_ind < max_ind)) {
			ports |= 0x01;
		}
		if (ports != 0) {
			_FSM_state = s_s0;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;

			return;
		}
		loop(data_src_O);
		i++;
		goto l_s0;
	} else {
		_FSM_state = s_s0;
		si->num_firings = i;
		si->reason = starved;
		si->ports = 0x00;
		return;
	}

l_s1:
	printf("stuck in state \"s1\"\n");
}

