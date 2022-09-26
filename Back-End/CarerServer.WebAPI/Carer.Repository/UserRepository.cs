using Carer.Model;
using Carer.Model.DomainModels;
using Carer.Repository.Common;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;

namespace Carer.Repository
{
    class UserRepository : IUserRepository
    {
        private String connectionString = "Data Source=SQL8004.site4now.net;Initial Catalog=db_a8bd39_carerdb;User Id=db_a8bd39_carerdb_admin;Password=Micamaca12";

        public async Task<List<UserDomainModel>> GetAllUsersAsync()
        {
            SqlConnection connection = new SqlConnection(connectionString);
            String queryString = "select * from [User]";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet userData = new DataSet();
            await Task.Run(() => (adapter.Fill(userData)));
            List<UserDomainModel> users = new List<UserDomainModel>();
            if (userData.Tables[0].Rows.Count == 0)
            {
                return users;
            }
            foreach (DataRow dataRow in userData.Tables[0].Rows)
            {
                users.Add(new UserDomainModel
                {
                    Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                    Username = Convert.ToString(dataRow["Username"]),
                    Email = Convert.ToString(dataRow["Email"]),
                    PhoneNumber = Convert.ToString(dataRow["Phonenumber"]),
                    Role = Convert.ToBoolean(dataRow["Role"]),
                    PictureURL = Convert.ToString(dataRow["PictureURL"]),
                    CarerOverallRating = Convert.ToInt32(dataRow["CarerOverallRating"]),
                    PetHandlingRating = Convert.ToInt32(dataRow["PetHandlingRating"]),
                    CarerCourtesyRating = Convert.ToInt32(dataRow["CarerCourtesyRating"]),
                    OwnerOverallRating = Convert.ToInt32(dataRow["OwnerOverallRating"]),
                    PetRating = Convert.ToInt32(dataRow["PetRating"]),
                    OwnerCourtesyRating = Convert.ToInt32(dataRow["OwnerCourtesyRating"]),
                    Bio = Convert.ToString(dataRow["Bio"]),
                });
            }
            return users;
        }

        public async Task<UserDomainModel> GetUserByIdAsync(Guid id)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            String queryString = $"select * from [User] where Id = '{id}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet userData = new DataSet();
            await Task.Run(() => (adapter.Fill(userData)));
            if (userData.Tables[0].Rows.Count == 0)
            {
                return null;
            }
            DataRow dataRow = userData.Tables[0].Rows[0];
            UserDomainModel user = new UserDomainModel
            {
                Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                Username = Convert.ToString(dataRow["Username"]),
                Email = Convert.ToString(dataRow["Email"]),
                PhoneNumber = Convert.ToString(dataRow["Phonenumber"]),
                Role = Convert.ToBoolean(dataRow["Role"]),
                PictureURL = Convert.ToString(dataRow["PictureURL"]),
                CarerOverallRating = Convert.ToInt32(dataRow["CarerOverallRating"]),
                PetHandlingRating = Convert.ToInt32(dataRow["PetHandlingRating"]),
                CarerCourtesyRating = Convert.ToInt32(dataRow["CarerCourtesyRating"]),
                OwnerOverallRating = Convert.ToInt32(dataRow["OwnerOverallRating"]),
                PetRating = Convert.ToInt32(dataRow["PetRating"]),
                OwnerCourtesyRating = Convert.ToInt32(dataRow["OwnerCourtesyRating"]),
                Bio = Convert.ToString(dataRow["Bio"]),
            };

            return user;
        }
        public async Task PostUserAsync(UserDomainModel user)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"INSERT INTO [User] (Id,Username,Email,PictureURL) VALUES ('{user.Id}','{user.Username}','{user.Email}','{user.PictureURL}')";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task PutUserAsync(UserDomainModel user)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE [User] SET Username = '{user.Username}', PhoneNumber = '{user.PhoneNumber}',Bio ='{user.Bio}' where Id = '{user.Id}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            //command.Parameters.Add("@DATEUPDATED", SqlDbType.DateTime);
            //command.Parameters["@DATEUPDATED"].Value = user.DateUpdated;
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task DeleteUserAsync(Guid id)
        {
            string deleteAds = $"DELETE FROM Ad WHERE UserId = '{id}';";
            string deleteApplies = $"DELETE FROM AppliedUser WHERE UserId = '{id}';";
            string deleteUser = $"delete from [User] where Id = '{id}'";
            SqlConnection connection = new SqlConnection(connectionString);
            SqlCommand command = new SqlCommand(deleteAds, connection);
            connection.Open();
            command.ExecuteNonQuery();

            command.CommandText = deleteApplies;
            command.ExecuteNonQuery();

            command.CommandText = deleteUser;
            command.ExecuteNonQuery();

            connection.Close();
        }

        public async Task<Guid> GetUserByEmail(string email)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"SELECT Id from [User] where Email = '{email}'";
            DataSet data = new DataSet();
            SqlDataAdapter adapter = new SqlDataAdapter(queryString, connection);
            await Task.Run(() => (adapter.Fill(data)));
            if(data.Tables[0].Rows.Count == 0)
            {
                return Guid.Empty;
            }
            DataRow dataRow = data.Tables[0].Rows[0];
            return Guid.Parse(Convert.ToString(dataRow["Id"]));


        }
        
        public void AddCarerReview(CarerReviewDomainModel review)
        {
            RatingsModel oldRatings = GetCurrentRatings(true, review.CarerId);
            RatingsModel newRatings = new RatingsModel
            {
                Count = oldRatings.Count + 1,
                OverallRating = calculateNewRating(oldRatings.OverallRating, review.OverallRating, oldRatings.Count),
                PetRating = calculateNewRating(oldRatings.PetRating, review.PetHandlingRating, oldRatings.Count),
                CourtesyRating = calculateNewRating(oldRatings.CourtesyRating, review.CourtesyRating, oldRatings.Count)
            };
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE [User] SET CarerOverallRating = {newRatings.OverallRating.ToString().Replace(",",".")}, PetHandlingRating = {newRatings.PetRating.ToString().Replace(",", ".")}, CarerCourtesyRating = {newRatings.CourtesyRating.ToString().Replace(",", ".")},NumberOfCarerReviews = {newRatings.Count} where Id = '{review.CarerId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            command.ExecuteNonQuery();
            connection.Close();
        }

        public async void AddOwnerReview(OwnerReviewDomainModel review)
        {
            RatingsModel oldRatings = GetCurrentRatings(false, review.OwnerId);
            RatingsModel newRatings = new RatingsModel
            {
                Count = oldRatings.Count + 1,
                OverallRating = calculateNewRating(oldRatings.OverallRating, review.OverallRating, oldRatings.Count),
                PetRating = calculateNewRating(oldRatings.PetRating, review.PetRating, oldRatings.Count),
                CourtesyRating = calculateNewRating(oldRatings.CourtesyRating, review.CourtesyRating, oldRatings.Count)
            };
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE [User] SET OwnerOverallRating = {newRatings.OverallRating.ToString().Replace(",", ".")}, PetRating = {newRatings.PetRating.ToString().Replace(",", ".")}, OwnerCourtesyRating = {newRatings.CourtesyRating.ToString().Replace(",", ".")},NumberOfOwnerReviews = {newRatings.Count} where Id = '{review.OwnerId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            command.ExecuteNonQuery();
            connection.Close();

        }

        public RatingsModel GetCurrentRatings(bool carerReviews,Guid userId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string countTaret;
            string overallRating;
            string petRating;
            string courtesyRating;
            if (carerReviews)
            {
                countTaret = "NumberOfCarerReviews";
                overallRating = "CarerOverallRating";
                petRating = "PetHandlingRating";
                courtesyRating = "CarerCourtesyRating";
            }
            else
            {
                countTaret = "NumberOfOwnerReviews";
                overallRating = "OwnerOverallRating";
                petRating = "PetRating";
                courtesyRating = "OwnerCourtesyRating";
            }
            string queryString = $"SELECT {countTaret},{overallRating},{petRating},{courtesyRating} from [User] where Id = '{userId}'";
            DataSet data = new DataSet();
            SqlDataAdapter adapter = new SqlDataAdapter(queryString, connection);
            adapter.Fill(data);
            DataRow dataRow = data.Tables[0].Rows[0];
            RatingsModel ratings = new RatingsModel
            {
                Count = Convert.ToInt32(dataRow[countTaret]),
                OverallRating = Convert.ToInt32(dataRow[overallRating]),
                PetRating = Convert.ToInt32(dataRow[petRating]),
                CourtesyRating = Convert.ToInt32(dataRow[overallRating])

            };
            return ratings;


        }

        public decimal calculateNewRating(decimal oldRating,decimal inputRating,int count)
        {
            return ((oldRating * count) + inputRating) / (count + 1);
        }


    }
}
