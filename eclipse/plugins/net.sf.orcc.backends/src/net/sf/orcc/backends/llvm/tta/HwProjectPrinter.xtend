/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta

import java.io.File
import net.sf.orcc.backends.llvm.tta.architecture.Design
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.util.FPGA
import net.sf.orcc.util.OrccUtil

class HwProjectPrinter extends TTAPrinter {
	
	private FPGA fpga;
	
	new(FPGA fpga) {
		this.fpga = fpga;
	}
	
	def print(Design design, String targetFolder) {
		if(fpga.altera) {
			val qpfFile = new File(targetFolder + File::separator + "top.qpf")
			val qcfFile = new File(targetFolder + File::separator + "top.qsf")
			OrccUtil::printFile(design.qpf, qpfFile)
			OrccUtil::printFile(design.qcf, qcfFile)
		} else if(fpga.xilinx) {
			val ucfFile = new File(targetFolder + File::separator + "top.ucf")
			val xiseFile = new File(targetFolder + File::separator + "top.xise")
			OrccUtil::printFile(design.ucf, ucfFile)
			OrccUtil::printFile(design.xise, xiseFile)
		}
	}
	
	def private getQcf(Design design) 
		'''
		# Quartus II Project
		# Generated by Orcc
		
		# Board informations
		set_global_assignment -name FAMILY "«fpga.family»"
		set_global_assignment -name DEVICE «fpga.device»
		set_global_assignment -name TOP_LEVEL_ENTITY top
		
		# Network
		set_global_assignment -name VHDL_FILE top.vhd
		
		«FOR processor:design.processors»
			«processor.qcf»
		«ENDFOR»
		
		# Shared components
		set_global_assignment -name VHDL_FILE share/vhdl/util_pkg.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/tce_util_pkg.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/rf_1wr_1rd_always_1_guarded_0.vhd
		set_global_assignment -name VHDL_FILE share/vhdl/mul.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/ldh_ldhu_ldq_ldqu_ldw_sth_stq_stw.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/and_ior_xor.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/add_and_eq_gt_gtu_ior_shl_shr_shru_sub_sxhw_sxqw_xor.vhdl
		set_global_assignment -name VHDL_FILE share/vhdl/stratix3_led_io_always_1.vhd
		
		# Other components
		set_global_assignment -name VHDL_FILE wrapper/altera_ram_1p.vhd
		set_global_assignment -name VHDL_FILE wrapper/altera_ram_2p.vhd
		set_global_assignment -name VHDL_FILE wrapper/altera_rom.vhd
		set_global_assignment -name VHDL_FILE interface/counter.vhd
		set_global_assignment -name VHDL_FILE interface/fps_eval.vhd
		set_global_assignment -name VHDL_FILE interface/segment_display_conv.vhd
		set_global_assignment -name VHDL_FILE interface/segment_display_sel.vhd
		
		# Pin assignments
		set_location_assignment PIN_F21 -to leds[0]
		set_location_assignment PIN_C23 -to leds[1]
		set_location_assignment PIN_B23 -to leds[2]
		set_location_assignment PIN_A23 -to leds[3]
		set_location_assignment PIN_D19 -to leds[4]
		set_location_assignment PIN_C19 -to leds[5]
		set_location_assignment PIN_F19 -to leds[6]
		set_location_assignment PIN_E19 -to leds[7]
		set_location_assignment PIN_AP5 -to rst_n
		set_location_assignment PIN_T33 -to clk
		
		set_location_assignment PIN_AE10 -to segment7[0]
		set_location_assignment PIN_AL5  -to segment7[1]
		set_location_assignment PIN_AC12 -to segment7[2]
		set_location_assignment PIN_AM5  -to segment7[3]
		set_location_assignment PIN_AF11 -to segment7[4]
		set_location_assignment PIN_AM6  -to segment7[5]
		set_location_assignment PIN_AP3  -to segment7[6]
		set_location_assignment PIN_AM4  -to segment7_sel[0]
		set_location_assignment PIN_AE12 -to segment7_sel[1]
		set_location_assignment PIN_AL4  -to segment7_sel[2]
		set_location_assignment PIN_AH8  -to segment7_sel[3]
		'''
		
	def private getQcf(Processor processor)
		'''
		# Processor «processor.name»
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/«processor.name»_tl_params_pkg.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/«processor.name»_tl.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/«processor.name»_tl_globals_pkg.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/«processor.name»_mem_constants_pkg.vhd
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/«processor.name».vhd
		set_global_assignment -name VHDL_FILE «processor.name»/tta/vhdl/imem_mau_pkg.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/output_socket_«processor.buses.size»_1.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/output_socket_1_1.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/input_socket_«processor.buses.size».vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/ifetch.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/idecompressor.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/ic.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/gcu_opcodes_pkg.vhdl
		set_global_assignment -name VHDL_FILE «processor.name»/tta/gcu_ic/decoder.vhdl
		'''
	
	def private getQpf(Design design)
		'''
		# Quartus II Project
		# Generated by Orcc
		
		PROJECT_REVISION = "top"
		'''
	
	def private getXise(Design design) 
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
		<project xmlns="http://www.xilinx.com/XMLSchema" xmlns:xil_pn="http://www.xilinx.com/XMLSchema">
		
		  <header>
		    <!-- ISE source project file created by the Open RVC-CAL Compiler      -->
		    <!--                                                                   -->
		    <!-- This file contains project source information including a list of -->
		    <!-- project source files, project and process properties.  This file, -->
		    <!-- along with the project source files, is sufficient to open and    -->
		    <!-- implement in ISE Project Navigator.                               -->
		    <!--                                                                   -->
		  </header>
		
		  <files>
		    <file xil_pn:name="top.ucf" xil_pn:type="FILE_UCF">
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		    <file xil_pn:name="top.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
			«FOR processor:design.processors»
				«processor.xise»
			«ENDFOR»
		
		    <!--                   -->
		    <!-- Shared components.-->
		    <!--                   -->
		    <file xil_pn:name="share/vhdl/util_pkg.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/tce_util_pkg.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/rf_1wr_1rd_always_1_guarded_0.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/mul.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/ldh_ldhu_ldq_ldqu_ldw_sth_stq_stw.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/and_ior_xor.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/add_and_eq_gt_gtu_ior_shl_shr_shru_sub_sxhw_sxqw_xor.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		    <!--                  -->
		    <!-- Other components.-->
		    <!--                  -->
		    <file xil_pn:name="interface/counter.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/fps_eval.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/segment_display_conv.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/segment_display_sel.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		  </files>
		
		  <properties>
		    <property xil_pn:name="Auto Implementation Top" xil_pn:value="false" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top" xil_pn:value="Architecture|top|bdf_type" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top File" xil_pn:value="top.vhd" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top Instance Path" xil_pn:value="/top" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Device Family" xil_pn:value="Virtex6" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Device" xil_pn:value="xc6vlx240t" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Package" xil_pn:value="ff1156" xil_pn:valueState="default"/>
		    <property xil_pn:name="Speed Grade" xil_pn:value="-1" xil_pn:valueState="non-default"/>
		  </properties>
		
		  <bindings/>
		
		  <libraries/>printUcf
		
		  <autoManagedFiles>
		    <!-- The following files are identified by `include statements in verilog -->
		    <!-- source files and are automatically managed by Project Navigator.     -->
		    <!--                                                                      -->
		    <!-- Do not hand-edit this section, as it will be overwritten when the    -->
		    <!-- project is analyzed based on files automatically identified as       -->
		    <!-- include files.                                                       -->
		  </autoManagedFiles>
		
		</project>
		'''
		
	def private getXise(Processor processor)
		'''
		<!-- Processor «processor.name» -->
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl_params_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl_globals_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name».vhd" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/imem_mau_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/output_socket_«processor.buses.size»_1.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/output_socket_1_1.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/input_socket_«processor.buses.size».vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulationprintUcf"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/ifetch.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/idecompressor.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/ic.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/gcu_opcodes_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/decoder.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/irom_«processor.name».ngc" xil_pn:type="FILE_NGC">
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/dram_«processor.name».ngc" xil_pn:type="FILE_NGC">
		  <association xil_pn:name="Implementation"/>
		</file>
		'''
	
	def private getUcf(Design design)
		'''
		NET "leds[0]" IOSTANDARD = LVCMOS18;
		NET "leds[0]" LOC = AC22;
		NET "leds[1]" IOSTANDARD = LVCMOS18;
		NET "leds[1]" LOC = AC24;
		NET "leds[2]" IOSTANDARD = LVCMOS18;
		NET "leds[2]" LOC = AE22;
		NET "leds[3]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[3]" LOC = AE23;
		NET "leds[4]" IOSTANDARD = LVCMOS18;
		NET "leds[4]" LOC = AB23;
		NET "leds[5]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[5]" LOC = AG23;
		NET "leds[6]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[6]" LOC = AE24;
		NET "leds[7]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[7]" LOC = AD24;
		
		NET "rst_n" TIG;
		//LVCMOS33
		NET "rst_n" IOSTANDARD = LVCMOS25;
		NET "rst_n" PULLUP;
		NET "rst_n" LOC = D22;
		
		NET "clk" TNM_NET = "sys_clk_pin";
		NET "clk" CLOCK_DEDICATED_ROUTE = FALSE;
		TIMESPEC TS_sys_clk_pin = PERIOD "sys_clk_pin" 100000 KHz;
		//LVCMOS33
		NET "clk" IOSTANDARD = LVCMOS25;
		NET "clk" LOC = AE16;
		'''
}