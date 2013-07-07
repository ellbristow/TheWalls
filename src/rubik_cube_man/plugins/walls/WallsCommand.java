package rubik_cube_man.plugins.walls;

import java.io.File;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kitteh.tag.TagAPI;

import rubik_cube_man.plugins.walls.blockFileData.ArenaFileManager;
import rubik_cube_man.plugins.walls.kits.KitFile;

public class WallsCommand implements CommandExecutor {
	
	private Walls plugin;
	private int num;

	public WallsCommand(Walls walls) {
		this.plugin = walls;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			if (args.length == 0){
				sender.sendMessage(ChatColor.GREEN + "The Walls Minigame Plugin made by rubik_cube_man, version 1.2.0.1");
			}
			else if (args[0].equalsIgnoreCase("help")){
					if (sender.hasPermission("walls.join")){
						sender.sendMessage(ChatColor.RED + "/Walls Join <ArenaName>  " + ChatColor.GREEN + "Joins an arena");
					}
					if (sender.hasPermission("walls.leave")){
						sender.sendMessage(ChatColor.RED + "/Walls Leave" + ChatColor.GREEN + "  Leaves the arena you are in");
					}
					if (sender.hasPermission("walls.list")){
						sender.sendMessage(ChatColor.RED + "/Walls List " + ChatColor.GREEN + " Lists the arenas that have been made");
					}
					if (sender.hasPermission("walls.create")){
						sender.sendMessage(ChatColor.RED + "/Walls Create <ArenaName>  " + ChatColor.GREEN + "Creates an arena");
						sender.sendMessage(ChatColor.RED + "/Walls AddSpawn <Colour> <ArenaName>  " + ChatColor.GREEN + "Adds a spawn point for an arena");
						sender.sendMessage(ChatColor.RED + "/Walls SetWarp <WarpName> <ArenaName>  " + ChatColor.GREEN + "Sets a warp for an arena");
						sender.sendMessage(ChatColor.RED + "/Walls Tool" + ChatColor.GREEN + "  Give you the region selecting tool");
						sender.sendMessage(ChatColor.RED + "/Walls BuildRegion <ArenaName>  " + ChatColor.GREEN + "Adds a region people can build in while in the walls");
						sender.sendMessage(ChatColor.RED + "/Walls DropLocation <ArenaName>  " + ChatColor.GREEN + "Adds blocks that will cause the walls to drop");
					}
					if (sender.hasPermission("walls.delete")){
						sender.sendMessage(ChatColor.RED + "/Walls Delete <ArenaName>  " + ChatColor.GREEN + "Deletes an arena");	
					}
					if (sender.hasPermission("walls.stop")){
						sender.sendMessage(ChatColor.RED + "/Walls Stop [ArenaName]  " + ChatColor.GREEN + "Stops an arena or all of the arenas");
					}
					if (sender.hasPermission("walls.start")){
						sender.sendMessage(ChatColor.RED + "/Walls Start [ArenaName]  " + ChatColor.GREEN + "Starts an arena");
					}
					if (sender.hasPermission("walls.info")){
						sender.sendMessage(ChatColor.RED + "/Walls Info <ArenaName>  " + ChatColor.GREEN + "Shows the infomation about an arena");
					}
					if (sender.hasPermission("walls.listregions")){
						sender.sendMessage(ChatColor.RED + "/Walls ListRegions <RegionName> <ArenaName>  " + ChatColor.GREEN + "Lists the arenas regions of that type");
					}
					if (sender.hasPermission("walls.showregions")){
						sender.sendMessage(ChatColor.RED + "/Walls ShowRegions <RegionName> [Region-ID] <ArenaName>" + ChatColor.GREEN + "  Shows regions with glowstone");
					}
					if (sender.hasPermission("walls.commands")){
						sender.sendMessage(ChatColor.RED + "/Walls AllowedCommands <Operator> [command] <ArenaName>  " + ChatColor.GREEN + "Shows the infomation about an arena");
					}
					if (sender.hasPermission("walls.save") || sender.hasPermission("walls.save.config")){
						sender.sendMessage(ChatColor.RED + "/Walls Save Config  " + ChatColor.GREEN + "Saves the config");
					}
					if (sender.hasPermission("walls.save") || sender.hasPermission("walls.save.arena")){
						sender.sendMessage(ChatColor.RED + "/Walls Save Arena <ArenaName>  " + ChatColor.GREEN + "Saves arena into a file");
					}
					if (sender.hasPermission("walls.checkupdates")){
						sender.sendMessage(ChatColor.RED + "/Walls CheckUpdate [True/False]" + ChatColor.GREEN + "  Sets if the plugin will look for updates");
					}
					if (sender.hasPermission("walls.players.othergames")){
						sender.sendMessage(ChatColor.RED + "/Walls Players [ArenaName]" + ChatColor.GREEN + "  Gets players in the arenas");
					}
					else if (sender.hasPermission("walls.players")){
						sender.sendMessage(ChatColor.RED + "/Walls Players" + ChatColor.GREEN + "  Gets the players that are in the arena with you");
					}
					if ((!sender.hasPermission("walls.create")) && (!sender.hasPermission("walls.list")) && (!sender.hasPermission("walls.join")) && (!sender.hasPermission("walls.delete")) && (!sender.hasPermission("walls.stop")) && (!sender.hasPermission("walls.start")) && (!sender.hasPermission("walls.info")) && (!sender.hasPermission("walls.listregions")) && (!sender.hasPermission("walls.showregions")) && (!sender.hasPermission("walls.players")) && (!sender.hasPermission("walls.players.othergames"))){
						sender.sendMessage(ChatColor.RED + "You can't use any commands from this plugin");
					}
				}
				else if (args[0].equalsIgnoreCase("create")){
					if (sender.hasPermission("walls.create")){
						if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								sender.sendMessage(ChatColor.RED + "Arena " + args[1].toLowerCase() + " already exists");
							}else{
								if (plugin.location1.get((Player)sender) != null){
									if (plugin.location2.get((Player)sender) != null){
										Player player = (Player) sender;
										if (plugin.location1.get((Player)sender).getWorld() == player.getWorld()){
											if (plugin.location2.get((Player)sender).getWorld() == player.getWorld()){
												Arena arena = new Arena(args[1].toLowerCase(), plugin.location1.get((Player)sender), plugin.location2.get((Player)sender), 0, false, null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, null, null, null, null, 0, false, 0, plugin);
												plugin.arenas.put(arena.getName(), arena);
												sender.sendMessage(ChatColor.GREEN + arena.getName() + " has been created!");
												sender.sendMessage(ChatColor.GOLD + "Saving the arena to a file!");
												ArenaFileManager.setPlugin(plugin);
												ArenaFileManager.saveToAFile(args[1].toLowerCase(), plugin.location1.get((Player)sender), plugin.location2.get((Player)sender));
												sender.sendMessage(ChatColor.GOLD + "Saved the arena!");
											}else{
												sender.sendMessage(ChatColor.RED + "Both your points need to be in the same world as you");
											}
										}else{
											sender.sendMessage(ChatColor.RED + "Both your points need to be in the same world as you");
										}
									}else{
										sender.sendMessage(ChatColor.RED + "You need to have set both points");
									}
								}else{
									sender.sendMessage(ChatColor.RED + "You need to have set both points");
								}
							}
					}else{
						sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls Create <arenaname>");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
				}
			}
				else if (args[0].equalsIgnoreCase("stop")){
					if (sender.hasPermission("walls.stop")){
						if (args.length == 1){
							Iterator<String> list = plugin.arenas.keySet().iterator();
							while(list.hasNext()){
								String name = list.next();
								if (!plugin.arenas.get(name).getPlayerList().isEmpty()){
									Iterator<Player> players = plugin.arenas.get(name).getPlayerList().keySet().iterator();
									while(players.hasNext()){
										Player player = players.next();
										plugin.arenas.get(name).setRandom(player);
										plugin.arenas.get(name).Leave();
										plugin.playerarena.remove(player);
										player.teleport(plugin.arenas.get(name).getLose());
									}
								}
								plugin.arenas.get(name).RestoreBlocks();
							    plugin.arenas.get(name).setRedplayers(0);
							    plugin.arenas.get(name).setBlueplayers(0);
							    plugin.arenas.get(name).setGreenplayers(0);
							    plugin.arenas.get(name).setYellowplayers(0);
							    plugin.arenas.get(name).setTotal(0);
							    plugin.arenas.get(name).setStarted(false);
							    plugin.arenas.get(name).getPlayerList().clear();
							    plugin.arenas.get(name).setCounter(null);
							    sender.sendMessage(ChatColor.RED + "All arenas been stopped");
							}
						}
					else if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								if (plugin.arenas.get(args[1].toLowerCase()).getStarted() == true){
									String arena = args[1].toLowerCase();
									Iterator<Player> players = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
									while(players.hasNext()){
										Player player = players.next();
										plugin.arenas.get(arena).setRandom(player);
										plugin.arenas.get(arena).Leave();
										plugin.playerarena.remove(player);
										player.teleport(plugin.arenas.get(arena).getLose());
									}
								    plugin.arenas.get(arena).RestoreBlocks();
								    plugin.arenas.get(arena).setRedplayers(0);
								    plugin.arenas.get(arena).setBlueplayers(0);
								    plugin.arenas.get(arena).setGreenplayers(0);
								    plugin.arenas.get(arena).setYellowplayers(0);
								    plugin.arenas.get(arena).setTotal(0);
								    plugin.arenas.get(arena).setStarted(false);
								    plugin.arenas.get(arena).getPlayerList().clear();
								    plugin.arenas.get(arena).setCounter(null);
								    sender.sendMessage(ChatColor.RED + "Arena " + args[1].toLowerCase() + " has been stopped");
								}else{
									sender.sendMessage(ChatColor.RED + args[1].toLowerCase() + " has not started so you can't stop it");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls Stop [Arenaname]");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
					}
				}
				else if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("forcestart")){
					if (sender.hasPermission("walls.start")){
						Player player = (Player) sender;
						if (args.length == 1){
							if (plugin.playerarena.get(player) != null){
								if (plugin.arenas.containsKey(plugin.playerarena.get(player).toLowerCase())){
									if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() >= 2){
										String arena = plugin.playerarena.get(player);
										sender.sendMessage(ChatColor.AQUA + "starting the arena " + arena);
										plugin.arenas.get(arena).setCounter(10);
									}
									else{
										sender.sendMessage(ChatColor.RED + "You need 2 people to start a game");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Error. Deleted arena.");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "You are not in an arena so can't use the command like that");
								sender.sendMessage("Use the command like this. " + ChatColor.GREEN + "/Walls Start <arenaname>");
							}
						}
						else if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								if (plugin.arenas.get(args[1].toLowerCase()).getStarted() == false){
									if (plugin.arenas.get(args[1].toLowerCase()).getPlayerInventories().size() >= 2){
										sender.sendMessage(ChatColor.AQUA + "starting the arena " + args[1].toLowerCase());
										plugin.arenas.get(args[1].toLowerCase()).setCounter(10);
									}
									else{
										sender.sendMessage(ChatColor.RED + "You need 2 people to start a game");
									}
								}else{
									sender.sendMessage(ChatColor.RED + args[1].toLowerCase() + " has already started");
								}
							}else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
					}else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("setwarp")){
					if (sender.hasPermission("walls.create")){
						if (args.length >= 3){
							Player player = (Player)sender;
							if (args[1].equalsIgnoreCase("win")){
								if (plugin.arenas.containsKey(args[2].toLowerCase())){
									plugin.arenas.get(args[2].toLowerCase()).setWin(player.getLocation());
									sender.sendMessage(ChatColor.GREEN + "Win point has been set for " + args[2].toLowerCase());
								}
								else{
									sender.sendMessage(ChatColor.RED + "That arena does not exist");
			
								}
							}
							else if (args[1].equalsIgnoreCase("lose")){
								if (plugin.arenas.containsKey(args[2].toLowerCase())){
									plugin.arenas.get(args[2].toLowerCase()).setLose(player.getLocation());
									sender.sendMessage(ChatColor.GREEN + "Lose point has been set for " + args[2].toLowerCase());
								}
								else{
									sender.sendMessage(ChatColor.RED + "That arena does not exist");
								}
							}
							else if (args[1].equalsIgnoreCase("lobby")){
								if (plugin.arenas.containsKey(args[2].toLowerCase())){
									plugin.arenas.get(args[2].toLowerCase()).setLobby(player.getLocation());
									sender.sendMessage(ChatColor.GREEN + "Lobby point has been set for " + args[2].toLowerCase());
								}
								else{
									sender.sendMessage(ChatColor.RED + "That arena does not exist");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That can't be a Warp. " + ChatColor.GREEN + "It can be: Win, Lose, Lobby");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls SetWarp <WarpName> <ArenaName>");
						}
					}
				}
				else if (args[0].equalsIgnoreCase("addspawn")){
					if (sender.hasPermission("walls.create")){
						if (args.length == 3){
							Player player = (Player)sender;
							if (plugin.arenas.containsKey(args[2].toLowerCase())){
								if (args[1].equalsIgnoreCase("red")){
									plugin.arenas.get(args[2].toLowerCase()).setPlayerLoc(player.getLocation());
									plugin.arenas.get(args[2].toLowerCase()).addRedSpawn();
									sender.sendMessage(ChatColor.DARK_RED + "Spawn " + plugin.arenas.get(args[2].toLowerCase()).getReds() + " set in " + args[2].toLowerCase());
									plugin.arenas.get(args[2].toLowerCase()).min();
									Iterator<Integer> itr = plugin.arenas.get(args[2].toLowerCase()).getSignWalls().keySet().iterator();
									while (itr.hasNext()){
										Integer num = itr.next();
										plugin.arenas.get(args[2].toLowerCase()).getSignWalls().get(num).addPlayerSign(plugin.arenas.get(args[2].toLowerCase()).getMin());
									}
								}
								else if (args[1].equalsIgnoreCase("blue")){
									plugin.arenas.get(args[2].toLowerCase()).setPlayerLoc(player.getLocation());
									plugin.arenas.get(args[2].toLowerCase()).addBlueSpawn();
									sender.sendMessage(ChatColor.DARK_BLUE + "Spawn " + plugin.arenas.get(args[2].toLowerCase()).getBlues() + " set in " + args[2].toLowerCase());
									plugin.arenas.get(args[2].toLowerCase()).min();
									Iterator<Integer> itr = plugin.arenas.get(args[2].toLowerCase()).getSignWalls().keySet().iterator();
									while (itr.hasNext()){
										Integer num = itr.next();
										plugin.arenas.get(args[2].toLowerCase()).getSignWalls().get(num).addPlayerSign(plugin.arenas.get(args[2].toLowerCase()).getMin());
									}
								}
								else if (args[1].equalsIgnoreCase("green")){
									plugin.arenas.get(args[2].toLowerCase()).setPlayerLoc(player.getLocation());
									plugin.arenas.get(args[2].toLowerCase()).addGreenSpawn();
									sender.sendMessage(ChatColor.DARK_GREEN + "Spawn " + plugin.arenas.get(args[2].toLowerCase()).getGreens() + " set in " + args[2].toLowerCase());
									plugin.arenas.get(args[2].toLowerCase()).min();
									Iterator<Integer> itr = plugin.arenas.get(args[2].toLowerCase()).getSignWalls().keySet().iterator();
									while (itr.hasNext()){
										Integer num = itr.next();
										plugin.arenas.get(args[2].toLowerCase()).getSignWalls().get(num).addPlayerSign(plugin.arenas.get(args[2].toLowerCase()).getMin());
									}
								}
								else if (args[1].equalsIgnoreCase("yellow")){
									plugin.arenas.get(args[2].toLowerCase()).setPlayerLoc(player.getLocation());
									plugin.arenas.get(args[2].toLowerCase()).addYellowSpawn();
									sender.sendMessage(ChatColor.YELLOW + "Spawn " + plugin.arenas.get(args[2].toLowerCase()).getYellows() + " set in " + args[2].toLowerCase());
									plugin.arenas.get(args[2].toLowerCase()).min();
									Iterator<Integer> itr = plugin.arenas.get(args[2].toLowerCase()).getSignWalls().keySet().iterator();
									while (itr.hasNext()){
										Integer num = itr.next();
										plugin.arenas.get(args[2].toLowerCase()).getSignWalls().get(num).addPlayerSign(plugin.arenas.get(args[2].toLowerCase()).getMin());
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "That can't be a team colour. " + ChatColor.GREEN + "It can be: Red, Blue, Green, Yellow");
								}
							}else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls AddSpawn <Colour> <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("join")){
					if (sender.hasPermission("walls.join")){
						if (plugin.playerarena.get((Player)sender) == null){
							if (args.length == 2){
								String arena = args[1].toLowerCase();
								if (plugin.arenas.containsKey(arena)){
									if (plugin.arenas.get(arena).getStarted() == false){
										if (plugin.arenas.get(arena).getLose() != null && plugin.arenas.get(args[1].toLowerCase()).getWin() != null && plugin.arenas.get(args[1].toLowerCase()).getLobby() != null){
											if(plugin.arenas.get(arena).getMin() > 0){
												if (plugin.arenas.get(arena).getDropTime() != null){
													Player player = (Player)sender;
													Integer num = plugin.arenas.get(arena).getMin() + plugin.arenas.get(args[1].toLowerCase()).getMin() + plugin.arenas.get(args[1].toLowerCase()).getMin() + plugin.arenas.get(args[1].toLowerCase()).getMin();
													if (plugin.arenas.get(arena).getTotal() < num){
														if (!plugin.arenas.get(arena).isResetting()){
															player.teleport(plugin.arenas.get(arena).getLobby());
															plugin.playerarena.put(player, arena);
												    		plugin.teleportable.put(player, false);
															plugin.arenas.get(arena).setTotal(plugin.arenas.get(arena).getTotal() + 1);
															sender.sendMessage(ChatColor.GRAY + "Joined arena " + arena + "!");
															plugin.arenas.get(args[1].toLowerCase()).getPlayerList().put(player , "lobby");
														    plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().put(player, "lobby");
														    plugin.arenas.get(arena).getPlayerInventories().put(player, player.getInventory().getContents());
														    plugin.arenas.get(arena).getAurmor().put(player , player.getInventory().getArmorContents());
														    Integer tempExp = (Integer)player.getLevel();
														    plugin.arenas.get(arena).getExp().put(player , tempExp);
														    player.getInventory().clear();
														    player.getInventory().setArmorContents(null);
														    player.updateInventory();
														    player.setLevel(0);
														    plugin.arenas.get(arena).getLobbyPlayers().add(player);
														    player.setExp(0);
														    player.setHealth(20);
														    player.setFoodLevel(20);
														    player.setGameMode(GameMode.ADVENTURE);
															plugin.arenas.get(arena).playerJoin();
															plugin.arenas.get(arena).playerAdd(player.getName());
															Iterator<Player> list2 = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
															while(list2.hasNext()){
																Player names = list2.next();
																Integer min4 = plugin.arenas.get(arena).getMin() * 4;
																names.sendMessage(ChatColor.DARK_PURPLE + sender.getName() + " has joined the lobby. " + plugin.arenas.get(args[1].toLowerCase()).getTotal() + "/" + min4);
															}
															if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == 2){
																plugin.arenas.get(arena).setCounter(80);
																Iterator<Player> list = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
																while(list.hasNext()){
																	Player names = list.next();
																	names.sendMessage(ChatColor.BLUE + "Game starting soon!");
																}
															}
														}
														else{
															player.sendMessage(ChatColor.RED + "That arena is resetting!");
														}
													}
													else{
														sender.sendMessage(ChatColor.RED + "This arena is full. Try again later!");
													}
												}
												else{
													sender.sendMessage(ChatColor.RED + "This arena does not have a set Time yet!");
												}
											}
											else{
												sender.sendMessage(ChatColor.RED + "This arena has not got any spawns for teams");
											}
										}else{
											sender.sendMessage(ChatColor.RED + "This arena has not been set up yet!");
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "That Game has already started!");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "That arena does not exist");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls Join <ArenaName>");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "You can't join an arena while in an arena!");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("listarenas")){
					if (sender.hasPermission("walls.list")){
						Integer arenas = 0;
						Iterator<String> count = plugin.arenas.keySet().iterator();
						while(count.hasNext()){
							count.next();
							arenas++;
						}
						sender.sendMessage(ChatColor.BLUE + "There are " + arenas + " arenas");
						Iterator<String> list = plugin.arenas.keySet().iterator();
						while(list.hasNext()){
							String name = list.next();
							sender.sendMessage(ChatColor.DARK_GREEN + "" + name);
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("dropregion") || args[0].equalsIgnoreCase("dropregions") || args[0].equalsIgnoreCase("droplocation") || args[0].equalsIgnoreCase("droplocations")){
					if (sender.hasPermission("walls.create")){
						if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								if (plugin.location1.get((Player)sender) != null){
									if (plugin.location2.get((Player)sender) != null){
										Player player = (Player) sender;
										if (plugin.location1.get((Player)sender).getWorld() == player.getWorld()){
											if (plugin.location2.get((Player)sender).getWorld() == player.getWorld()){
												plugin.arenas.get(args[1].toLowerCase()).setTempLoc(plugin.location1.get(player));
												plugin.arenas.get(args[1].toLowerCase()).setTempLoc1(plugin.location2.get(player));
												Integer num = plugin.arenas.get(args[1].toLowerCase()).getTotalDrops() + 1;
												sender.sendMessage(ChatColor.GOLD + "Drop Location number " + num + " set for arena " + args[1].toLowerCase());
												plugin.arenas.get(args[1].toLowerCase()).CreateDropLocation();
											}else{
												sender.sendMessage(ChatColor.RED + "Both your points need to be in the same world as you");
											}
										}else{
											sender.sendMessage(ChatColor.RED + "Both your points need to be in the same world as you");
										}
									}else{
										sender.sendMessage(ChatColor.RED + "You need to have set both points");
									}
								}else{
									sender.sendMessage(ChatColor.RED + "You need to have set both points");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls DropLocation <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("leave")){
					if (sender.hasPermission("walls.leave")){
						Player player = (Player)sender;
						String arena = plugin.playerarena.get(player);
						if (plugin.arenas.containsKey(plugin.playerarena.get(player))){
							sender.sendMessage(ChatColor.RED + "You left the arena!");
				    		plugin.teleportable.put(player, true);
							if (plugin.arenas.get(arena).getCounter() != null && plugin.arenas.get(arena).getCounter() < 10){
								Iterator<Player> list = plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet().iterator();
								while(list.hasNext()){
									Player name = list.next();
									if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "red"){
										name.sendMessage(ChatColor.RED + "" + player.getName() + ChatColor.AQUA + " has left the arena");
									}
									else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "blue"){
										name.sendMessage(ChatColor.BLUE + "" + player.getName() + ChatColor.AQUA + " has left the arena");
									}
									else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "green"){
										name.sendMessage(ChatColor.GREEN + "" + player.getName() + ChatColor.AQUA + " has left the arena");		
									}
									else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "yellow"){
										name.sendMessage(ChatColor.YELLOW + "" + player.getName() + ChatColor.AQUA + " has left the arena!");	
									}
								}
								TagAPI.refreshPlayer(player);
							    plugin.arenas.get(plugin.playerarena.get(player)).setRandom(player);
							    plugin.arenas.get(plugin.playerarena.get(player)).Leave();
							    plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().remove(player);
							    player.setHealth(20);
							    player.setFoodLevel(20);
							    player.setFireTicks(0);
							    if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getRedplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getBlueplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getGreenplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getYellowplayers()){
							    	Arena arenas = plugin.arenas.get(plugin.playerarena.get(player));
							    	arenas.broadcastWinner();
							    	for (Player play : plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet()){
							    		plugin.teleportable.put(play, true);
							    		arenas.setRandom(play);
							    		arenas.Leave();
								    	plugin.playerarena.remove(play);
							    		play.teleport(arenas.getWin());
							    		play.setHealth(20);
							    		play.setFoodLevel(20);
							    		play.setFallDistance(0);
							    		play.setFireTicks(0);
							    	}
							    	arenas.getPlayerList().clear();
								    plugin.arenas.get(plugin.playerarena.get(player)).setCountToEnd(0);
							    }
							    plugin.playerarena.remove(player);
						    }
							else {
								plugin.arenas.get(arena).getLobbyPlayers().remove(player);
								if (plugin.arenas.get(arena).getTotal() <= 1){
									Iterator<Player> list = plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet().iterator();
									while(list.hasNext()){
										Player name = list.next();
										name.sendMessage(ChatColor.AQUA + "" + player.getName() + " has left the lobby!");
									}
								}
							    plugin.arenas.get(arena).setRandom(player);
							    plugin.arenas.get(arena).Leave();
							    player.setHealth(20);
							    player.setFoodLevel(20);
							    player.setFireTicks(0);
							    if (plugin.arenas.get(arena).getTotal() >= 1){
									Iterator<Player> list3 = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
									while(list3.hasNext()){
										Player names = list3.next();
										names.sendMessage(ChatColor.AQUA + "" + player.getName() + " has left the lobby!");
									}
							    }
							    plugin.playerarena.remove(player);
							    plugin.arenas.get(arena).getPlayerList().remove(player);
							    if (plugin.arenas.get(arena).getTotal() == 1){
							    	plugin.arenas.get(arena).setCounter(null);
							    	if (plugin.arenas.get(arena).getTotal() >= 1){
									Iterator<Player> list2 = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
										while(list2.hasNext()){
											Player names = list2.next();
											names.sendMessage(ChatColor.RED + "Game stopped because there are not enough people");
										}
									}
							    }
							    player.setGameMode(GameMode.SURVIVAL);
							    player.teleport(plugin.arenas.get(arena).getLose());
							    plugin.arenas.get(arena).playerJoin();
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "You are not in any arena!");
						}
					}
				}
				else if (args[0].equalsIgnoreCase("buildregions") || args[0].equalsIgnoreCase("buildregion") || args[0].equalsIgnoreCase("buildlocation") || args[0].equalsIgnoreCase("buildlocations")){
					if (sender.hasPermission("walls.create")){
						if (args.length >= 2){
							Player player = (Player) sender;
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								if (plugin.location1.get(player) != null && plugin.location2.get(player) != null){
									plugin.arenas.get(args[1].toLowerCase()).setSaveTotal(plugin.arenas.get(args[1].toLowerCase()).getSaveTotal() + 1);
									Location start1 = plugin.location1.get(player);
									Location start2 = plugin.location2.get(player);
									Integer maxX = Math.max((int)start1.getX(), (int)start2.getX());
									Integer minX = Math.min((int)start1.getX(), (int)start2.getX());
									Integer maxY = Math.max((int)start1.getY(), (int)start2.getY());
									Integer minY = Math.min((int)start1.getY(), (int)start2.getY());
									Integer maxZ = Math.max((int)start1.getZ(), (int)start2.getZ());
									Integer minZ = Math.min((int)start1.getZ(), (int)start2.getZ());
									World world = plugin.location1.get(player).getWorld();
									Location end1 = world.getBlockAt(maxX,maxY,maxZ).getLocation();
									Location end2 = world.getBlockAt(minX, minY, minZ).getLocation();
									plugin.arenas.get(args[1].toLowerCase()).getSaveregion().put(plugin.arenas.get(args[1].toLowerCase()).getSaveTotal(), end1);
									plugin.arenas.get(args[1].toLowerCase()).getSaveregion1().put(plugin.arenas.get(args[1].toLowerCase()).getSaveTotal(), end2);
									sender.sendMessage(ChatColor.GOLD + "Added build region number " + plugin.arenas.get(args[1].toLowerCase()).getSaveTotal());
								}
								else{
									sender.sendMessage(ChatColor.RED + "You need to have set both points");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls BuildRegion <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("tool") || args[0].equalsIgnoreCase("wand")){
					if (sender.hasPermission("walls.create")){
						Player player = (Player) sender;
						ItemStack is = new ItemStack(Material.CLAY_BRICK, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Walls Tool");
						is.setItemMeta(im);
						player.getInventory().addItem(is);
						sender.sendMessage(ChatColor.GOLD + "Here's that very useful tool");
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("arenainfo")){
					if (sender.hasPermission("walls.info")){
						if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								sender.sendMessage(ChatColor.GREEN + "Arena name: " + plugin.arenas.get(args[1].toLowerCase()).getName());
								sender.sendMessage(ChatColor.RED + "Reds: " + plugin.arenas.get(args[1].toLowerCase()).getReds());
								sender.sendMessage(ChatColor.BLUE + "Blues: " + plugin.arenas.get(args[1].toLowerCase()).getBlues());
								sender.sendMessage(ChatColor.GREEN + "Greens: " + plugin.arenas.get(args[1].toLowerCase()).getGreens());
								sender.sendMessage(ChatColor.YELLOW + "Yellows: " + plugin.arenas.get(args[1].toLowerCase()).getYellows());
								if (plugin.arenas.get(args[1].toLowerCase()).getWin() == null){
									sender.sendMessage(ChatColor.GRAY + "WinPoint: False");
								}
								else{
									sender.sendMessage(ChatColor.GRAY + "WinPoint: True");
								}
								if (plugin.arenas.get(args[1].toLowerCase()).getLose() == null){
									sender.sendMessage(ChatColor.GRAY + "LosePoint: False");
								}
								else{
									sender.sendMessage(ChatColor.GRAY + "LosePoint: True");
								}
								if (plugin.arenas.get(args[1].toLowerCase()).getLobby() == null){
									sender.sendMessage(ChatColor.GRAY + "LobbyPoint: False");
								}
								else{
									sender.sendMessage(ChatColor.GRAY + "LobbyPoint: True");
								}
								Integer num = 0;
								Iterator<Integer> location = plugin.arenas.get(args[1].toLowerCase()).getDroploc1().keySet().iterator();
								while (location.hasNext()){
									num++;
									location.next();
								}
								sender.sendMessage(ChatColor.GRAY + "Drop Locations: " + num);
								sender.sendMessage(ChatColor.GRAY + "Build Locations: " + plugin.arenas.get(args[1].toLowerCase()).getSaveregion().size());
								if (plugin.arenas.get(args[1].toLowerCase()).getDropTime() == null || plugin.arenas.get(args[1].toLowerCase()).getDropTime() == 0){
									sender.sendMessage(ChatColor.DARK_GRAY + "Time: Not Set");
								}
								else{
									sender.sendMessage(ChatColor.DARK_GRAY + "Time: " + plugin.arenas.get(args[1].toLowerCase()).getDropTime() + " second(s)");
								}
								Iterator<Integer> broadcasts = plugin.arenas.get(args[1].toLowerCase()).getMessages().keySet().iterator();
								String message = "";
								while (broadcasts.hasNext()){
									message = message + broadcasts.next() + ", ";
								}
								sender.sendMessage(ChatColor.DARK_GRAY + "BroadCasts: " + message);
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls Info <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")){
					if (sender.hasPermission("walls.delete")){
						if (args.length >= 2){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								plugin.arenas.remove(args[1].toLowerCase());
								plugin.DeleteArena = args[1].toLowerCase();
								plugin.DeleteArena();
								sender.sendMessage(ChatColor.AQUA + "Arena Deleted!");
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls Delete <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("listregions") || args[0].equalsIgnoreCase("listregion")){
					if (sender.hasPermission("walls.listregions")){
						if (args.length >= 3){
							if (plugin.arenas.containsKey(args[2].toLowerCase())){
								if (args[1].equalsIgnoreCase("buildregions") || args[1].equalsIgnoreCase("buildregion") || args[1].equalsIgnoreCase("buildregions")){
									if (!plugin.arenas.get(args[2].toLowerCase()).getSaveregion().isEmpty()){
										Iterator<Integer> locations = plugin.arenas.get(args[2].toLowerCase()).getSaveregion().keySet().iterator();
										while (locations.hasNext()){
											Integer loc = locations.next();
											sender.sendMessage(ChatColor.RED + "" + loc + ":");
											sender.sendMessage(ChatColor.GOLD + "Point1- X: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion().get(loc).getX() + " Y: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion().get(loc).getY() + " Z: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion().get(loc).getZ());
											sender.sendMessage(ChatColor.GOLD + "Point2- X: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion1().get(loc).getX() + " Y: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion1().get(loc).getY() + " Z: " + plugin.arenas.get(args[2].toLowerCase()).getSaveregion1().get(loc).getZ());										
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "There are no build regions yet");
									}
								}
								else if (args[1].equalsIgnoreCase("dropregions") || args[1].equalsIgnoreCase("dropregion") || args[1].equalsIgnoreCase("dropregions")){
									if (!plugin.arenas.get(args[2].toLowerCase()).getDrop().isEmpty()){
										Iterator<Integer> locations = plugin.arenas.get(args[2].toLowerCase()).getDroploc1().keySet().iterator();
										while (locations.hasNext()){
											Integer loc = locations.next();
											sender.sendMessage(ChatColor.RED + "" + loc + ":");
											sender.sendMessage(ChatColor.GOLD + "Point1- X: " + plugin.arenas.get(args[2].toLowerCase()).getDroploc1().get(loc).getX() + " Y:" + plugin.arenas.get(args[2].toLowerCase()).getDroploc1().get(loc).getY() + " Z:" + plugin.arenas.get(args[2].toLowerCase()).getDroploc1().get(loc).getZ());
											sender.sendMessage(ChatColor.GOLD + "Point2- X: " + plugin.arenas.get(args[2].toLowerCase()).getDroploc2().get(loc).getX() + " Y:" + plugin.arenas.get(args[2].toLowerCase()).getDroploc2().get(loc).getY() + " Z:" + plugin.arenas.get(args[2].toLowerCase()).getDroploc2().get(loc).getZ());
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "There are no drop regions yet");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Unknown region name.  " + ChatColor.GREEN + "Choose from: Build, Drop");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls ListRegions <RegionName> <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("showregions") || args[0].equalsIgnoreCase("showregion") || args[0].equalsIgnoreCase("showlocation") || args[0].equalsIgnoreCase("showlocations")){
					if (sender.hasPermission("walls.showregions")){
						if (args.length >= 3){
							if (args[1].equalsIgnoreCase("buildlocations") || args[1].equalsIgnoreCase("buildregion") || args[0].equalsIgnoreCase("buildregion") || args[0].equalsIgnoreCase("buildregions")){
								if (args.length >= 4){
									if (plugin.arenas.containsKey(args[3].toLowerCase())){
										try{
											num = Integer.parseInt(args[2]);
										} catch (NumberFormatException e){
											sender.sendMessage(ChatColor.RED + "The Region-ID is going to be a number");
										}
										if (plugin.arenas.get(args[3].toLowerCase()).getSaveregion().containsKey((Integer)num)){
											if (!plugin.arenas.get(args[3].toLowerCase()).isShowing()){
												sender.sendMessage(ChatColor.GOLD + "Showing the region...");
												plugin.arenas.get(args[3].toLowerCase()).setSeeing((Player)sender);
												plugin.arenas.get(args[3].toLowerCase()).setNum((Integer)num);
												plugin.arenas.get(args[3].toLowerCase()).showRegion();
											}
											else{
												sender.sendMessage(ChatColor.RED + "A region is already being shown");
											}
										}
										else{
											sender.sendMessage(ChatColor.RED + "Unknown region ID");
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "That arena does not exist");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls ShowRegion Build <Build-Region-ID> <ArenaName>");
								}
							}
							else if (args[1].equalsIgnoreCase("arena") || args[1].equalsIgnoreCase("arenaregion") || args[1].equalsIgnoreCase("arenalocation") || args[1].equalsIgnoreCase("arenalocations") || args[1].equalsIgnoreCase("arenaregions")){
								if (plugin.arenas.containsKey(args[2].toLowerCase())){
									plugin.arenas.get(args[2].toLowerCase()).setSeeing((Player)sender);
									sender.sendMessage(ChatColor.GOLD + "Showing the region...");
									plugin.arenas.get(args[2].toLowerCase()).ShowArena();
								}
								else{
									sender.sendMessage(ChatColor.RED + "That arena does not exist");
								}
							}
							else if (args[1].equalsIgnoreCase("droplocation") || args[1].equalsIgnoreCase("droplocations") || args[1].equalsIgnoreCase("dropregion") || args[1].equalsIgnoreCase("dropregions")){
								if (plugin.arenas.containsKey(args[3].toLowerCase())){
									
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown region name.  " + ChatColor.GREEN + "Choose from: BuildRegion, ArenaRegion");
							}
						}
					else{
						sender.sendMessage(ChatColor.RED + "Use the command like this. " + ChatColor.GREEN + "/Walls ShowRegion <Region Name> [Region-ID] <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("time")){
					if (sender.hasPermission("walls.time")){
						if (args.length >= 2){
							if (args[1].equalsIgnoreCase("set")){
								if (args.length >= 4){
									if (plugin.arenas.containsKey(args[3].toLowerCase())){
										Integer time = 0;
										try{
											time = Integer.parseInt(args[2]);
										}catch (NumberFormatException e){
											sender.sendMessage(ChatColor.RED + "Needs to be a whole number");
										}
										if (time > 0){
											plugin.arenas.get(args[3].toLowerCase()).setDropTime(time);
											Integer seconds = time;
											Integer min = 0;
											while (seconds >= 60){
												seconds = seconds - 60;
												min++;
											}
											if (min != 0 && seconds == 0){
												sender.sendMessage(ChatColor.GOLD + "Total time set to " + min + " minute(s) for the arena " + args[3].toLowerCase());
											}
											else if (seconds != 0 && min == 0){
												sender.sendMessage(ChatColor.GOLD + "Total time set to " + seconds + " second(s) for the arena " + args[3].toLowerCase());
											}
											else if (seconds != 0 && min != 0){
												sender.sendMessage(ChatColor.GOLD + "Total time set to " + min + " minute(s) and " + seconds + " second(s) for the arena " + args[3].toLowerCase());
											}
										}
										else{
											sender.sendMessage(ChatColor.RED + "The time you need to set must be 1 second or more");
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "That arena does not exist");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Time Set <Time> <ArenaName>");
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "Unknown operator. Choose for" + ChatColor.GREEN + " Set");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Time <Operator> [Value] <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("broadcast") || args[0].equalsIgnoreCase("broadcasts")){
					if (sender.hasPermission("walls.broadcast")){
						if (args.length >= 2){
							if (args[1].equalsIgnoreCase("add")){
								if (args.length >= 4){
									if (plugin.arenas.containsKey(args[3].toLowerCase())){
										Integer time = 0;
										try{
											time = Integer.parseInt(args[2]);
										}catch (NumberFormatException e){
											sender.sendMessage(ChatColor.RED + "Needs to be a whole number");
										}
										if (!plugin.arenas.get(args[3].toLowerCase()).getMessages().containsKey(time)){
											if (plugin.arenas.get(args[3].toLowerCase()).getDropTime() > time){
												if (time > 0){
													plugin.arenas.get(args[3].toLowerCase()).getMessages().put(time, "");
													Integer seconds = time;
													Integer min = 0;
													while (seconds >= 60){
														seconds = seconds - 60;
														min++;
													}
													if (min != 0 && seconds == 0){
														sender.sendMessage(ChatColor.GOLD + "A broadcast has been set to " + min + " minute(s) for the arena " + args[3].toLowerCase());
													}
													else if (seconds != 0 && min == 0){
														sender.sendMessage(ChatColor.GOLD + "A broadcast has been set to " + seconds + " second(s) for the arena " + args[3].toLowerCase());
													}
													else if (seconds != 0 && min != 0){
														sender.sendMessage(ChatColor.GOLD + "A broadcast has been set to " + min + " minute(s) and " + seconds + " second(s) for the arena " + args[3].toLowerCase());
													}
												}
												else{
													sender.sendMessage(ChatColor.RED + "The time you want to add must be 1 second or more");
												}
											}
											else{
												sender.sendMessage(ChatColor.RED + "You can't have a message that is going to be before the game starts, Make the number smaller");
											}
										}
										else{
											sender.sendMessage(ChatColor.RED + "You are already broadcasting a message then!");
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "That arena does not exist");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Broadcast Add <Time> <ArenaName>");
								}
							}
							else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")){
								if (args.length >= 4){
									if (plugin.arenas.containsKey(args[3].toLowerCase())){
										Integer timer = 0;
										try{
											timer = Integer.parseInt(args[2]);
										}catch (NumberFormatException e){
											sender.sendMessage(ChatColor.RED + "The time that you put in must be a whole number");
										}
										if (plugin.arenas.get(args[3].toLowerCase()).getMessages().containsKey(timer)){
											plugin.arenas.get(args[3].toLowerCase()).getMessages().remove(timer);
											Integer seconds = timer;
											Integer min = 0;
											while (seconds >= 60){
												seconds = seconds - 60;
												min++;
											}
											if (min != 0 && seconds == 0){
												sender.sendMessage(ChatColor.GOLD + "Deleted Broadcast at the time " + min + " minute(s) for the arena " + args[3].toLowerCase());
											}
											else if (seconds != 0 && min == 0){
												sender.sendMessage(ChatColor.GOLD + "Deleted Broadcast at the time " + seconds + " second(s) for the arena " + args[3].toLowerCase());
											}
											else if (seconds != 0 && min != 0){
												sender.sendMessage(ChatColor.GOLD + "Deleted Broadcast at the time " + min + " minute(s) and " + seconds + " second(s) for the arena " + args[3].toLowerCase());
											}
										}
										else{
											sender.sendMessage(ChatColor.RED + "There is no broadcast at that time so can't delete it!");
										}
									}
									else{
										sender.sendMessage(ChatColor.RED + "That arena does not exist");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Broadcasts Remove <Time> <ArenaName>");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown operator. Choose for" + ChatColor.GREEN + " Add, Remove");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Broadcast <Operator> [Value] <ArenaName>");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("allowedcommands") || args[0].equals("allowedcommand") || args[0].equalsIgnoreCase("allowedcommands") || args[0].equalsIgnoreCase("allowedcommand") || args[0].equalsIgnoreCase("ac")){
					if (sender.hasPermission("walls.commands")){
						if (args.length >= 2){
							if (args[1].equalsIgnoreCase("add")){
								if (args.length == 3){
									if (!plugin.allowedCommands.contains(args[2].toLowerCase())){
										sender.sendMessage(ChatColor.GOLD + "The command " + args[2].toLowerCase() + " has been allowed in the walls!");
										plugin.allowedCommands.add(args[2].toLowerCase());
									}
									else{
										sender.sendMessage(ChatColor.RED + "That command has already been blocked");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls AllowedCommand Add <AllowedCommand>");
								}
							}
							else if (args[1].equalsIgnoreCase("list")){
								Iterator<String> allowedCommand = plugin.allowedCommands.iterator();
								String commands = ChatColor.DARK_GRAY + "Allowed Commands: ";
								while (allowedCommand.hasNext()){
									commands = commands + allowedCommand.next() + ChatColor.WHITE + ", " + ChatColor.DARK_GRAY + "";
								}
								sender.sendMessage(commands);
							}
							else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")){
								if (args.length == 3){
									if (plugin.allowedCommands.contains(args[2].toLowerCase())){
										sender.sendMessage(ChatColor.GOLD + "The command " + args[2].toLowerCase() + " has been blocked again!");
										plugin.allowedCommands.remove(args[2].toLowerCase());
									}
									else{
										sender.sendMessage(ChatColor.RED + "That command has not been allowed so you can't block it again");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls AllowedCommand Remove <AllowedCommand>");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown operator. Choose for" + ChatColor.GREEN + " Add, Remove, List");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls AllowedCommand <Operator> [Value]");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("checkupdates")){
					if (sender.hasPermission("walls.checkupdates")){
						if (args.length == 1){
							sender.sendMessage(ChatColor.GOLD + "Checking for updates = " + plugin.checkForUpdates);
						}
						else{
							if (args[1].equalsIgnoreCase("true")){
								sender.sendMessage(ChatColor.GOLD + "The plugin will now check for updates");
								plugin.checkForUpdates = true;
								plugin.setUpdatesOn();
							}
							else if (args[1].equalsIgnoreCase("false")){
								sender.sendMessage(ChatColor.GOLD + "The plugin will now no longer check for updates");
								plugin.checkForUpdates = false;
								plugin.setUpdatesOff();
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown Option. Choose from" + ChatColor.GREEN + " True, False");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("player")){
					if (args.length == 1){
						if (sender.hasPermission("walls.players")){
							Player player = (Player)sender;
							Arena arena = plugin.arenas.get(plugin.playerarena.get(player));
							if (plugin.playerarena.get((Player)sender) != null){
								if (arena.getStarted()){
									String reds = ChatColor.RED + "Reds: " + ChatColor.DARK_GRAY + " ";
									String blues = ChatColor.BLUE + "Blues: " + ChatColor.DARK_GRAY + " ";
									String greens = ChatColor.GREEN + "Greens: " + ChatColor.DARK_GRAY + " ";
									String yellows = ChatColor.YELLOW + "Yellows: " + ChatColor.DARK_GRAY + " ";
									Iterator<Player> itr = arena.getPlayerList().keySet().iterator();
									while (itr.hasNext()){
										Player play = itr.next();
										if (arena.getPlayerList().get(play) == "red"){
											reds = reds + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "blue"){
											blues = blues + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "green"){
											greens = greens + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "yellow"){
											yellows = yellows + play.getName() + ", ";
										}
									}
									sender.sendMessage("" + reds);
									sender.sendMessage("" + blues);
									sender.sendMessage("" + greens);
									sender.sendMessage("" + yellows);
								}
								else {
									String lobbyPlayers = ChatColor.GOLD + "Lobby: " + ChatColor.DARK_GRAY;
									for (Player p : arena.getLobbyPlayers()){
										lobbyPlayers = lobbyPlayers + p.getName() + "";
									}
									sender.sendMessage(lobbyPlayers);
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "You are not in an arena. Please state an arena name!");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
						}
					}
					else{
						if (sender.hasPermission("walls.players.othergames")){
							if (plugin.arenas.containsKey(args[1].toLowerCase())){
								if (plugin.arenas.get(args[1].toLowerCase()).getStarted()){
									Arena arena = plugin.arenas.get(args[1].toLowerCase());
									Iterator<Player> itr = arena.getPlayerList().keySet().iterator();
									String reds = ChatColor.RED + "Reds: " + ChatColor.DARK_GRAY + " ";
									String blues = ChatColor.BLUE + "Blues: " + ChatColor.DARK_GRAY + " ";
									String greens = ChatColor.GREEN + "Greens: " + ChatColor.DARK_GRAY + " ";
									String yellows = ChatColor.YELLOW + "Yellows: " + ChatColor.DARK_GRAY + " ";
									while (itr.hasNext()){
										Player play = itr.next();
										if (arena.getPlayerList().get(play) == "red"){
											reds = reds + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "blue"){
											blues = blues + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "green"){
											greens = greens + play.getName() + ", ";
										}
										if (arena.getPlayerList().get(play) == "yellow"){
											yellows = yellows + play.getName() + ", ";
										}
									}
									sender.sendMessage("" + reds);
									sender.sendMessage("" + blues);
									sender.sendMessage("" + greens);
									sender.sendMessage("" + yellows);
								}
								else {
									Arena arena = plugin.arenas.get(args[1].toLowerCase());
									String lobbyPlayers = ChatColor.GOLD + "Lobby: " + ChatColor.DARK_GRAY;
									for (Player p : arena.getLobbyPlayers()){
										lobbyPlayers = lobbyPlayers + p.getName() + "";
									}
									sender.sendMessage(lobbyPlayers);
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "That arena does not exist");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
						}
					}
				}
				else if (args[0].equalsIgnoreCase("save")){
					if (sender.hasPermission("walls.save") || sender.hasPermission("walls.save.config") || sender.hasPermission("walls.save.arena")){
						if (args.length >= 2){
							if (args[1].equalsIgnoreCase("config")){
								if (sender.hasPermission("walls.save") || sender.hasPermission("walls.save.config")){
									plugin.SaveConfig();
									sender.sendMessage(ChatColor.GREEN + "Saved the config!");
								}
								else{
									sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
								}
							}
							else if(args[1].equalsIgnoreCase("arena")){
								if (args.length >= 3){
									if (plugin.arenas.containsKey(args[2].toLowerCase())){
										sender.sendMessage(ChatColor.GOLD + "Saving the arena!");
										Arena arena = plugin.arenas.get(args[2].toLowerCase());
										ArenaFileManager.saveToAFile(args[2].toLowerCase(), arena.getLoc1(), arena.getLoc2());
										sender.sendMessage(ChatColor.GOLD + "Saved the arena!");
									}
									else{
										sender.sendMessage(ChatColor.RED + "That arena does not exist");
									}
								}
								else{
									sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Save Arena <ArenaName>");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown file to save, Choose from: " + ChatColor.GREEN + "Config, Arena");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Save <Config/Arena> [ArenaName]");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else if (args[0].equalsIgnoreCase("reload")){
					if (sender.hasPermission("walls.reload") || sender.hasPermission("walls.reload.kits")){
						if (args.length >= 2){
							if (args[1].equalsIgnoreCase("kits")){
								if (sender.hasPermission("walls.reload") || sender.hasPermission("walls.reload.kits")){
									KitFile.kitsList.clear();
									KitFile.saveDefaultKits(new File(plugin.getDataFolder() + File.separator + "kits.yml"));
									KitFile.loadKit(new File(plugin.getDataFolder() + File.separator + "kits.yml"));
									sender.sendMessage(ChatColor.GREEN + "Reloaded all the kits!");
								}
								else{
									sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
								}
							}
							else{
								sender.sendMessage(ChatColor.RED + "Unknown file to reload, Choose from: " + ChatColor.GREEN + "Kits");
							}
						}
						else{
							sender.sendMessage(ChatColor.RED + "Use the command like this " + ChatColor.GREEN + "/Walls Reload Kits");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You do not have permission to use that command");
					}
				}
				else{
					sender.sendMessage(ChatColor.RED + "Unknown sub-command. Type /walls help for more info!");
				}
			}
		else{
			sender.sendMessage(ChatColor.GREEN + "Only players can use that command");
		}
		return true;
	}
}