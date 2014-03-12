/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.c.hls

import java.io.File
import java.util.Map
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.util.OrccUtil

/**
 * generates top Network testbench
 *  
 * @author Khaled Jerbi
 * 
 */
class ActorNetworkTestBenchPrinter extends net.sf.orcc.backends.c.InstancePrinter {

	new(Map<String, Object> options) {
		super(options)
	}

	override print(String targetFolder) {

		val contentNetwork = actorNetworkFileContent
		val NetworkFile = new File(
			targetFolder + File::separator + instance.name + "TopVHDL" + File::separator + instance.name +
				"_TopTestBench" + ".vhd")

		if (needToWriteFile(contentNetwork, NetworkFile)) {
			OrccUtil::printFile(contentNetwork, NetworkFile)
			return 0
		} else {
			return 1
		}
	}

	def actorNetworkFileContent() '''
				LIBRARY ieee;
				USE ieee.std_logic_1164.ALL;
				USE ieee.std_logic_unsigned.all;
				USE ieee.numeric_std.ALL;
				USE std.textio.all;
				
				LIBRARY work;
				USE work.sim_package.all;
				
				ENTITY testbench IS
				END testbench;
				
				ARCHITECTURE behavior OF testbench IS
				
				-- Component Declaration
				COMPONENT TopDesign
				PORT(
				ap_clk : IN STD_LOGIC;
				ap_rst : IN STD_LOGIC;
				ap_start : IN STD_LOGIC;
				ap_done : OUT STD_LOGIC;
				ap_idle : OUT STD_LOGIC;
				ap_ready : OUT STD_LOGIC;
				
				
				
				«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort != null»
								«instance.incomingPortMap.get(port).fifoName»_V_dout   : IN STD_LOGIC_VECTOR («instance.incomingPortMap.get(
			port).fifoTypeIn.sizeInBits - 1» downto 0);
								«instance.incomingPortMap.get(port).fifoName»_V_empty_n : IN STD_LOGIC;
								«instance.incomingPortMap.get(port).fifoName»_V_read    : OUT STD_LOGIC;
								
								«connection.castfifoNameRead»_V_din    : OUT STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
								«connection.castfifoNameRead»_V_full_n : IN STD_LOGIC;
								«connection.castfifoNameRead»_V_write  : OUT STD_LOGIC;
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ELSE»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort == null»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   : IN STD_LOGIC_VECTOR («instance.incomingPortMap.
			get(port).fifoTypeIn.sizeInBits - 1» downto 0);
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n : IN STD_LOGIC;
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    : OUT STD_LOGIC;
							
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_din    : OUT STD_LOGIC_VECTOR («instance.outgoingPortMap.
			get(portout).head.fifoTypeOut.sizeInBits - 1» downto 0);
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_full_n : IN STD_LOGIC;
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_write  : OUT STD_LOGIC;
						«ELSE»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   : IN STD_LOGIC_VECTOR («instance.incomingPortMap.
			get(port).fifoTypeIn.sizeInBits - 1» downto 0);
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n : IN STD_LOGIC;
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    : OUT STD_LOGIC;
							
							«connection.castfifoNameRead»_V_din    : OUT STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
							«connection.castfifoNameRead»_V_full_n : IN STD_LOGIC;
							«connection.castfifoNameRead»_V_write  : OUT STD_LOGIC;
							
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
				
			«ENDIF»
				«ENDFOR»
				
				ap_return : OUT STD_LOGIC_VECTOR (31 downto 0)
			 );
				END COMPONENT;	
				
				signal ap_clk :  STD_LOGIC:= '0';
				signal ap_rst : STD_LOGIC:= '0';
				signal ap_start : STD_LOGIC:= '0';
				signal ap_done :  STD_LOGIC;
				signal ap_idle :  STD_LOGIC;
				signal ap_ready :  STD_LOGIC;
				
				«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort != null»
								«instance.incomingPortMap.get(port).fifoName»_V_dout   :  STD_LOGIC_VECTOR («instance.incomingPortMap.get(
			port).fifoTypeIn.sizeInBits - 1» downto 0);
								«instance.incomingPortMap.get(port).fifoName»_V_empty_n :  STD_LOGIC;
								«instance.incomingPortMap.get(port).fifoName»_V_read    :  STD_LOGIC;
								
								«connection.castfifoNameRead»_V_din    :  STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
								«connection.castfifoNameRead»_V_full_n :  STD_LOGIC;
								«connection.castfifoNameRead»_V_write  :  STD_LOGIC;
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ELSE»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort == null»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   :  STD_LOGIC_VECTOR («instance.incomingPortMap.
			get(port).fifoTypeIn.sizeInBits - 1» downto 0);
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n :  STD_LOGIC;
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    :  STD_LOGIC;
							
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_din    :  STD_LOGIC_VECTOR («instance.outgoingPortMap.
			get(portout).head.fifoTypeOut.sizeInBits - 1» downto 0);
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_full_n :  STD_LOGIC;
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_write  :  STD_LOGIC;
						«ELSE»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   :  STD_LOGIC_VECTOR («instance.incomingPortMap.
			get(port).fifoTypeIn.sizeInBits - 1» downto 0);
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n :  STD_LOGIC;
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    :  STD_LOGIC;
							
							«connection.castfifoNameRead»_V_din    :  STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
							«connection.castfifoNameRead»_V_full_n :  STD_LOGIC;
							«connection.castfifoNameRead»_V_write  :  STD_LOGIC;
							
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
				
			«ENDIF»
				«ENDFOR»
				
				signal ap_return :  STD_LOGIC_VECTOR (31 downto 0):= (others => '0');
				
				-- Configuration
				signal count       : integer range 255 downto 0 := 0;
				
				constant PERIOD : time := 20 ns;
				constant DUTY_CYCLE : real := 0.5;
				constant OFFSET : time := 100 ns;
				
				type severity_level is (note, warning, error, failure);
				type tb_type is (after_reset, read_file, CheckRead);
				
			 -- Input and Output files
				signal tb_FSM_bits  : tb_type;
				
				«FOR connection : instance.outgoingPortMap.values»
					file sim_file_«instance.name»_«connection.head.sourcePort.name»  : text is "«instance.name»_«connection.head.
			sourcePort.name».txt";
				«ENDFOR»
				«FOR connection : instance.incomingPortMap.values»
					file sim_file_«instance.name»_«connection.targetPort.name»  : text is "«instance.name»_«connection.targetPort.name».txt";
				«ENDFOR»
				
				begin
				
				uut : TopDesign port map (
				ap_clk => ap_clk,
				ap_rst => ap_rst,
				ap_start => ap_start,
				ap_done => ap_done,
				ap_idle => ap_idle,
				ap_ready =>ap_ready,
				
				«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort != null»
								«instance.incomingPortMap.get(port).fifoName»_V_dout  => «instance.incomingPortMap.get(port).fifoName»_V_dout,
								«instance.incomingPortMap.get(port).fifoName»_V_empty_n => «instance.incomingPortMap.get(port).fifoName»_V_empty_n,
								«instance.incomingPortMap.get(port).fifoName»_V_read    => «instance.incomingPortMap.get(port).fifoName»_V_read,
								
								«connection.castfifoNameRead»_V_din    => «connection.castfifoNameRead»_V_din,
								«connection.castfifoNameRead»_V_full_n => «connection.castfifoNameRead»_V_full_n,
								«connection.castfifoNameRead»_V_write  => «connection.castfifoNameRead»_V_write,
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ELSE»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort == null»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_dout,
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_empty_n,
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_read,
							
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_din    => «instance.outgoingPortMap.get(portout).head.
			fifoName»_V_din,
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_full_n => «instance.outgoingPortMap.get(portout).head.
			fifoName»_V_full_n,
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_write  => «instance.outgoingPortMap.get(portout).head.
			fifoName»_V_write,
						«ELSE»
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_dout   => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_dout,
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_empty_n => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_empty_n,
							«instance.incomingPortMap.get(port).castfifoNameWrite»_V_read    => «instance.incomingPortMap.get(port).
			castfifoNameWrite»_V_read,
							
							«connection.castfifoNameRead»_V_din    => «connection.castfifoNameRead»_V_din,
							«connection.castfifoNameRead»_V_full_n => «connection.castfifoNameRead»_V_full_n,
							«connection.castfifoNameRead»_V_write  => «connection.castfifoNameRead»_V_write,
							
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
				
			«ENDIF»
				«ENDFOR»
				
				ap_return => ap_return);
				
				clockProcess : process
		 begin
		 wait for OFFSET;
		 clock_LOOP : loop
		  ap_clk <= '0';
		        wait for (PERIOD - (PERIOD * DUTY_CYCLE));
		        ap_clk <= '1';
		        wait for (PERIOD * DUTY_CYCLE);
		  end loop clock_LOOP;
		 end process;
				
				
				resetProcess : process
		 begin                
		    wait for OFFSET;
		    -- reset state for 100 ns.
		    ap_rst <= '1';
		    wait for 100 ns;
		    ap_rst <= '0';        
		    wait;
		 end process;
				
				
				
				
				
				
				WaveGen_Proc_In : process (ap_clk)
		  variable Input_bit   : integer range 2147483647 downto - 2147483648;
		  variable line_number : line;
				
				«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
variable count«instance.incomingPortMap.get(port).fifoName»: integer:= 0;
			«ELSE»
				variable count«instance.incomingPortMap.get(port).castfifoNameWrite»: integer:= 0;
			«ENDIF»
					«ENDFOR»
					begin
			  if rising_edge(ap_clk) then
					
					«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
				«printInputWaveGen(instance, instance.incomingPortMap.get(port), instance.incomingPortMap.get(port).fifoName)»
			«ELSE»
				«printInputWaveGen(instance, instance.incomingPortMap.get(port),
			instance.incomingPortMap.get(port).castfifoNameWrite)»
			«ENDIF»
					«ENDFOR»
					end if;
					end process WaveGen_Proc_In;
				
		
					
				WaveGen_Proc_Out : process (ap_clk)
				variable Input_bit   : integer range 2147483647 downto - 2147483648;
				variable line_number : line;
				«FOR port : instance.getActor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(port)»
				«IF connection.targetPort == null»
variable count«connection.fifoName»: integer:= 0;
				«ELSE»
					variable count«connection.castfifoNameRead»: integer:= 0;
				«ENDIF»
			«ENDFOR»
				«ENDFOR»
				begin
				if (rising_edge(ap_clk)) then
			
				
				«FOR port : instance.getActor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(port)»
				«IF connection.targetPort == null»
					«printOutputWaveGen(instance, connection,connection.fifoName )»
				«ELSE»
					«printOutputWaveGen(instance, connection,connection.castfifoNameRead)»
				«ENDIF»
			«ENDFOR»
				«ENDFOR»
					
				end if;
				end process WaveGen_Proc_Out;
		
			
			
			
					
				«FOR port : instance.getActor.inputs»			
			«IF instance.incomingPortMap.get(port).sourcePort == null»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort != null»
									«connection.castfifoNameRead»_V_full_n <= '1';						
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ELSE»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort == null»	
							«instance.outgoingPortMap.get(portout).head.fifoName»_V_full_n <= '1';
						«ELSE»
							«connection.castfifoNameRead»_V_full_n <= '1';							
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
				
			«ENDIF»
				«ENDFOR»
			
			
			
			
			
			END;
	'''

	def assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputFifoAssignHLS(connList.head)»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port)»
				«printInputFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''

	def assignFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputSignalFifoAssignHLS(connList.head)»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port)»
				«printInputSignalFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''

	def printOutputFifoAssignHLS(Connection connection) '''
		
		«connection.fifoName»_din    : OUT STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
		«connection.fifoName»_full_n : IN STD_LOGIC;
		«connection.fifoName»_write  : OUT STD_LOGIC;
	'''

	def printInputFifoAssignHLS(Connection connection) '''
		
		«connection.fifoName»_dout   : IN STD_LOGIC_VECTOR («connection.fifoTypeIn.sizeInBits - 1» downto 0);
		«connection.fifoName»_empty_n : IN STD_LOGIC;
		«connection.fifoName»_read    : OUT STD_LOGIC;
	'''

	def printOutputSignalFifoAssignHLS(Connection connection) '''
		
		signal «connection.fifoName»_din    :  STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0) := (others => '0');
		signal «connection.fifoName»_full_n :  STD_LOGIC := '0';
		signal «connection.fifoName»_write  :  STD_LOGIC := '0';
	'''

	def printInputSignalFifoAssignHLS(Connection connection) '''
		
			signal «connection.fifoName»_dout   :  STD_LOGIC_VECTOR («connection.fifoTypeIn.sizeInBits - 1» downto 0) := (others => '0');
			signal «connection.fifoName»_empty_n :  STD_LOGIC := '0';
			signal «connection.fifoName»_read    :  STD_LOGIC := '0';
	'''

	def assignFifoFile(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				file sim_file_«instance.name»_«connList.head.sourcePort.name»  : text is "«instance.name»_«connList.head.sourcePort.
			name».txt";
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port)»
				file sim_file_«instance.name»_«connList.targetPort.name»  : text is "«instance.name»_«connList.targetPort.name».txt";
			«ENDIF»
		«ENDFOR»
	'''

	def mappingFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputFifoMappingHLS(connList.head)»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port
			) && !(connList.target instanceof Port)»
				«printInputFifoMappingHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''

	def printOutputFifoMappingHLS(Connection connection) '''
		«connection.fifoName»_din    => «connection.fifoName»_din,
		«connection.fifoName»_full_n => «connection.fifoName»_full_n,
		«connection.fifoName»_write  => «connection.fifoName»_write,
	'''

	def printInputFifoMappingHLS(Connection connection) '''
		«connection.fifoName»_dout    => «connection.fifoName»_dout,
		«connection.fifoName»_empty_n => «connection.fifoName»_empty_n,
		«connection.fifoName»_read    => «connection.fifoName»_read,
	'''



	def printInputWaveGen(Instance instance, Connection connection, CharSequence Fname) '''
		case tb_FSM_bits is
			when after_reset =>
			count <= count + 1;
			if (count = 15) then
			tb_FSM_bits <= read_file;
			count           <= 0;
		end if;
		
		when read_file =>
		if (not endfile (sim_file_«instance.name»_«connection.targetPort.name»)) then
			readline(sim_file_«instance.name»_«connection.targetPort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoTypeIn.int»
					«Fname»_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«IF connection.fifoTypeIn.uint»
					«Fname»_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«IF connection.fifoTypeIn.bool»
					if (input_bit = 1) then 
					«Fname»_dout  <= "1";
					else
					«Fname»_dout  <= "0";
					end if;
				«ENDIF»
				«Fname»_empty_n <= '1';
				ap_start <= '1';    
				tb_FSM_bits <= CheckRead;
			end if;
		end if;
		
		when CheckRead =>
		if (not endfile (sim_file_«instance.name»_«connection.targetPort.name»)) and «Fname»_read = '1' then
		 count«Fname» := count«Fname» + 1;
		 report "Number of inputs«Fname» = " & integer'image(count«Fname»);
		 «Fname»_empty_n <= '0';
		 readline(sim_file_«instance.name»_«connection.targetPort.name», line_number);
		 if (line_number'length > 0 and line_number(1) /= '/') then
		 	read(line_number, input_bit);
		 	«IF connection.fifoTypeIn.int»
		 		«Fname»_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoTypeIn.sizeInBits»));
		 	«ENDIF»
		 	«IF connection.fifoTypeIn.uint»
		 		«Fname»_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeIn.sizeInBits»));
		 	«ENDIF»
		 	«Fname»_empty_n <= '1';
		 	«IF connection.fifoTypeIn.bool»
		 		if (input_bit = 1) then 
		 		«Fname»_dout  <= "1";
		 		else
		 		«Fname»_dout  <= "0";
		 		end if;
		 	«ENDIF»
		 	ap_start <= '1';      
		end if;
			elsif (endfile (sim_file_«instance.name»_«connection.targetPort.name»)) then
				ap_start <= '1';
				«Fname»_empty_n <= '0';
			end if;
		when others => null;
		end case;
	'''

	def printOutputWaveGen(Instance vertex, Connection connection,CharSequence Fname) '''
		if (not endfile (sim_file_«vertex.name»_«connection.sourcePort.name») and «Fname»_write = '1') then
		count«Fname» := count«connection.fifoName» + 1;
		 report "Number of outputs«Fname» = " & integer'image(count«Fname»);
		 readline(sim_file_«vertex.name»_«connection.sourcePort.name», line_number);
		 if (line_number'length > 0 and line_number(1) /= '/') then
		 	read(line_number, input_bit);
		 	«IF connection.fifoTypeOut.int»
		 		assert («Fname»_din  = std_logic_vector(to_signed(input_bit, «connection.fifoTypeOut.sizeInBits»)))
		 		-- report "on «Fname» incorrectly value computed : " & to_string(to_integer(to_signed(«connection.
			fifoName»_din))) & " instead of :" & to_string(input_bit)
		 		report "on port «connection.fifoName» incorrectly value computed : " & str(to_integer(signed(«Fname»_din))) & " instead of :" & str(input_bit)
		 		severity error;
		 	«ENDIF»
		 	«IF connection.fifoTypeOut.uint»
		 		assert («Fname»_din  = std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeOut.sizeInBits»)))
		 		-- report "on «Fname» incorrectly value computed : " & to_string(to_integer(to_unsigned(«Fname»_din))) & " instead of :" & to_string(input_bit)
		 		report "on port «Fname» incorrectly value computed : " & str(to_integer(unsigned(«Fname»_din))) & " instead of :" & str(input_bit)
		 		severity error;
		 	«ENDIF»
		 	«IF connection.fifoTypeOut.bool»
		 		if (input_bit = 1) then
		 			assert («Fname»_din  = "1")
		 			report "on port «Fname» 0 instead of 1"
		 			severity error;
		 		else
		 			assert («Fname»_din  = "0")
		 			report "on port «Fname» 1 instead of 0"
		 			severity error;
		 		end if;
		 	«ENDIF»
		 	
		 
		 	--assert («Fname»_din /= std_logic_vector(to_signed(input_bit, «connection.fifoTypeOut.sizeInBits»)))
		 	--report "on port «Fname» correct value computed : " & str(to_integer(signed(«Fname»_din))) & " equals :" & str(input_bit)
		 	--severity note;
		
			end if;
		end if;
	'''

	def initOutputs(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«connList.head.fifoName»_full_n <= '1';
			«ENDIF»
		«ENDFOR»
	'''

	def fifoName(Connection connection) '''myStream_«connection.getAttribute("id").objectValue»'''

	def castfifoNameWrite(Connection connection) '''«IF connection != null»myStream_cast_«connection.getAttribute("id").
		objectValue»_write«ENDIF»'''

	def castfifoNameRead(Connection connection) '''«IF connection != null»myStream_cast_«connection.getAttribute("id").
		objectValue»_read«ENDIF»'''

	def fifoTypeOut(Connection connection) {
		if (connection.sourcePort == null) {
			connection.targetPort.type
		} else {
			connection.sourcePort.type
		}
	}

	def fifoTypeIn(Connection connection) {
		if (connection.targetPort == null) {
			connection.sourcePort.type
		} else {
			connection.targetPort.type
		}
	}
}
