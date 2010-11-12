-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : fifo.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-11-12
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
-- Copyright (c) 2009-2010, IETR/INSA of Rennes
-- All rights reserved.
-- 
-- Redistribution and use in source and binary forms, with or without
-- modification, are permitted provided that the following conditions are met:
-- 
--  -- Redistributions of source code must retain the above copyright notice,
--     this list of conditions and the following disclaimer.
--  -- Redistributions in binary form must reproduce the above copyright notice,
--     this list of conditions and the following disclaimer in the documentation
--     and/or other materials provided with the distribution.
--  -- Neither the name of the LEAD TECH DESIGN and INSA/IETR nor the names of its
--     contributors may be used to endorse or promote products derived from this
--     software without specific prior written permission.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author       Description
-- 2010-02-09  1.0      Nicolas      Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------

entity controler is
  generic (
    depth : integer := 32;
    width : integer := 32);
  port (
    reset_n : in  std_logic;
    rd_clk  : in  std_logic;
    rd_ack  : in  std_logic;
    rd_send : out std_logic;
    rd_add  : out std_logic_vector(bit_width(depth)-1 downto 0);
    wr_clk  : in  std_logic;
    wr_data : in  std_logic;
    wr_add  : out std_logic_vector(bit_width(depth)-1 downto 0);
    --
    empty   : out std_logic;
    full    : out std_logic
    );
end controler;

-------------------------------------------------------------------------------

architecture archcontroler of controler is


  signal inFull      : std_logic;
  signal inReadAdd   : std_logic_vector(bit_width(depth)-1 downto 0);
  signal inWriteAdd  : std_logic_vector(bit_width(depth)-1 downto 0);


begin

  -- Address management
  rd_add <= inReadAdd;
  wr_add <= inWriteAdd;
  counter_1 : entity work.counter
    generic map (
      depth => depth)
    port map (
      reset_n  => reset_n,
      rd_clk   => rd_clk,
      rd_ack   => rd_ack,
      wr_clk   => wr_clk,
      wr_data  => wr_data,
      rd_add   => inReadAdd,
      wr_add   => inWriteAdd);

  
  -- Flags
  full <= inFull;
  Flag_full : process (inReadAdd, inWriteAdd) is
    variable level : integer range depth -1 downto 0 := 0;
  begin
    if (inWriteAdd <= inReadAdd) then
      level := to_integer(unsigned(inReadAdd - inWriteAdd));
    else
      level := (depth -1) - to_integer(unsigned(inWriteAdd - inReadAdd));
    end if;
    
    case level is
      when 2 =>
        inFull <= '1';
      when 1 =>        
        inFull <= '1';
      when others =>
        inFull <= '0';
    end case;
  end process Flag_full;

  Flag_empty : process (inReadAdd, reset_n, inWriteAdd) is
  begin
    if reset_n = '0' then
      empty   <= '1';
      rd_send <= '0';
    elsif (inReadAdd = inWriteAdd) then
      empty   <= '1';
      rd_send <= '0';
    else
      empty   <= '0';
      rd_send <= '1';
    end if;
  end process Flag_empty;


end archcontroler;

