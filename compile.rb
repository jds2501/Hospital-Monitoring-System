files = Dir.glob(ARGV[0] + "/*.csv")

out = File.open(ARGV[1],"w")
files.each do |name|
  file = File.open(name, 'r')
  file.each do |line|
    out.puts(line)
  end
end
